package teamDBMS.sDrumGuitarBE.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
public class InvoiceUpdateRequest {

    @NotNull
    private Invoice.InvoiceStatus status;

    @NotNull
    private Invoice.PaymentMethod method;

    @NotNull
    @JsonProperty("paid_at")
    private Instant paidAt;

}