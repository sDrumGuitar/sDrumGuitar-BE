package teamDBMS.sDrumGuitarBE.invoice.service;

import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.CreateInvoiceRequest;
import teamDBMS.sDrumGuitarBE.invoice.dto.StudentInvoiceItem;
import teamDBMS.sDrumGuitarBE.invoice.dto.StudentInvoiceListResponse;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.invoice.repository.InvoiceRepository;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceSerive {
    private final InvoiceRepository invoiceRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Invoice createInvoice(Course course, Student student, CreateInvoiceRequest invReq) {
        if (invReq == null) throw new IllegalArgumentException("invoice is required");
        invReq.validate();

        Invoice invoice = Invoice.builder()
                .course(course)
                .student(student)
                .status(invReq.getStatus())
                .method(invReq.getMethod())
                .paidAt(invReq.getPaidAt())
                .build();

        return invoiceRepository.save(invoice);
    }

    public StudentInvoiceListResponse getStudentInvoices(Long studentId) {

        // 1️⃣ 학생 존재 확인
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
                );

        // 2️⃣ 청구서 조회 (최신순)
        List<Invoice> invoices =
                invoiceRepository.findByStudentIdOrderByIssuedAtDesc(studentId);

        // 3️⃣ DTO 변환
        List<StudentInvoiceItem> items = invoices.stream()
                .map(invoice -> StudentInvoiceItem.builder()
                        .invoiceId(invoice.getId())
                        .enrollmentId(invoice.getCourse().getId())
                        .issuedAt(invoice.getIssuedAt())
                        .paidAt(invoice.getPaidAt())
                        .status(invoice.getStatus().name().toLowerCase())
                        .method(invoice.getMethod() != null
                                ? invoice.getMethod().name().toLowerCase()
                                : null)
                        .lessonCount(invoice.getCourse().getLessonCount())
                        .familyDiscount(student.getFamilyDiscount())
                        .classType(invoice.getCourse().getClassType().name())
                        .totalAmount(
                                invoice.getTotalAmount() != null
                                        ? invoice.getTotalAmount()
                                        : calculateAmount(invoice)
                        )
                        .build()
                )
                .toList();

        return new StudentInvoiceListResponse(studentId, items);
    }

    private Integer calculateAmount(Invoice invoice) {
        // 정책 예시
        int base = invoice.getCourse().getLessonCount() * 50000;

        if (invoice.getStudent().getFamilyDiscount()) {
            return (int) (base * 0.9);
        }
        return base;
    }


}