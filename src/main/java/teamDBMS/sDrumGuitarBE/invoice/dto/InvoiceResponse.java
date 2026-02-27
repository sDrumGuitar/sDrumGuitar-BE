package teamDBMS.sDrumGuitarBE.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    @JsonProperty("invoice_id")
    private Long invoiceId;

    private Invoice.InvoiceStatus status;
    private Invoice.PaymentMethod method;

    @JsonProperty("paid_at")
    private Instant paidAt;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("student_id")
    private Long studentId;

    public static InvoiceResponse from(Invoice saved) {
        return InvoiceResponse.builder()
                .invoiceId(saved.getId())
                .status(saved.getStatus())
                .method(saved.getMethod())
                .paidAt(saved.getPaidAt())
                .courseId(saved.getCourse() == null ? null : saved.getCourse().getId())
                .studentId(saved.getStudent() == null ? null : saved.getStudent().getId())
                .build();
    }
}
