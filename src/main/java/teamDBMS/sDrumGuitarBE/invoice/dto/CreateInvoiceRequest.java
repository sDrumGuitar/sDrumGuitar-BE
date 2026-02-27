package teamDBMS.sDrumGuitarBE.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateInvoiceRequest {

    @NotNull
    private Invoice.InvoiceStatus status; // PAID / UNPAID

    private Invoice.PaymentMethod method; // CARD / CASH (optional)

    @JsonProperty("paid_at")
    private Instant paidAt; // "2026-09-28T14:10:00"

    public void validate() {
        if (status == null) throw new IllegalArgumentException("status is required");

        if (status == Invoice.InvoiceStatus.PAID) {
            if (paidAt == null) throw new IllegalArgumentException("paidAt is required when status is PAID");
            if (method == null) throw new IllegalArgumentException("method is required when status is PAID");
        }

        if (status == Invoice.InvoiceStatus.UNPAID) {
            if (paidAt != null) throw new IllegalArgumentException("paidAt must be null when status is UNPAID");
            if (method != null) throw new IllegalArgumentException("method must be null when status is UNPAID");
        }
    }

}
