package teamDBMS.sDrumGuitarBE.invoice.service;

import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.CreateInvoiceRequest;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.invoice.repository.InvoiceRepository;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

@Service
@RequiredArgsConstructor
public class InvoiceSerive {
    private final InvoiceRepository invoiceRepository;

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



}