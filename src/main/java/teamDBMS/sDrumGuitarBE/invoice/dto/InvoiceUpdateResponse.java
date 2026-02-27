package teamDBMS.sDrumGuitarBE.invoice.dto;

import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
public class InvoiceUpdateResponse {

    private Long invoiceId;
    private Long courseId;
    private Long studentId;

    private Invoice.InvoiceStatus status;
    private Invoice.PaymentMethod method;
    private Instant paidAt;

    private Instant updatedAt;

    public static InvoiceUpdateResponse from(Invoice invoice) {
        return InvoiceUpdateResponse.builder()
                .invoiceId(invoice.getId())
                .courseId(invoice.getCourse().getId())
                .studentId(invoice.getStudent().getId())
                .status(invoice.getStatus())
                .method(invoice.getMethod())
                .paidAt(invoice.getPaidAt())
                .updatedAt(invoice.getUpdatedAt())
                .build();
    }
}