package teamDBMS.sDrumGuitarBE.invoice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.*;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.invoice.repository.InvoiceRepository;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
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

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
                );

        Boolean familyDiscount = student.getFamilyDiscount();

        List<Invoice> invoices =
                invoiceRepository.findByStudentIdOrderByIssuedAtDesc(studentId);

        List<StudentInvoiceItem> items = invoices.stream()
                .map(invoice -> {

                    Course course = invoice.getCourse();

                    Integer lessonCount = course.getLessonCount();
                    String classType = course.getClassType().name();

                    return StudentInvoiceItem.builder()
                            .invoiceId(invoice.getId())
                            .enrollmentId(course.getId())
                            .issuedAt(invoice.getIssuedAt())
                            .paidAt(invoice.getPaidAt())
                            .status(invoice.getStatus().name().toLowerCase())
                            .method(invoice.getMethod() != null
                                    ? invoice.getMethod().name().toLowerCase()
                                    : null)
                            .lessonCount(lessonCount)
                            .familyDiscount(familyDiscount)
                            .classType(classType)
                            .totalAmount(
                                    calculateAmount(lessonCount, familyDiscount)
                            )
                            .build();
                })
                .toList();

        return new StudentInvoiceListResponse(studentId, items);
    }

    private Integer calculateAmount(Integer lessonCount, Boolean familyDiscount) {

        int amount = switch (lessonCount) {
            case 4 -> amount = 110_000;
            case 8 -> amount = 210_000;
            case 12 -> amount = 310_000;
            default -> throw new IllegalArgumentException("잘못된 회차 정책입니다.");
        };

        if (Boolean.TRUE.equals(familyDiscount)) {
            amount -= 10_000;
        }

        return amount;
    }

    @Transactional
    public InvoiceUpdateResponse updateInvoice(
            Long invoiceId,
            InvoiceUpdateRequest request
    ) {
        // 청구서 검색
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Invoice not found"
                        )
                );

        // 유효성 검증
        if (request.getStatus() == Invoice.InvoiceStatus.PAID) {

            Invoice.PaymentMethod method = request.getMethod();
            Instant paidAt = request.getPaidAt();

            if (method == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Payment method is required when status is PAID"
                );
            }

            if (paidAt == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "paidAt is required when status is PAID"
                );
            }

            invoice.markPaid(method,paidAt);
        }

        return InvoiceUpdateResponse.from(invoice);
    }
}