package teamDBMS.sDrumGuitarBE.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InvoiceSummary {
    private Long invoiceId;
    private String method;
    private String status;
    private LocalDateTime paidAt;

    public static InvoiceSummary from(Invoice invoice) {
        if (invoice == null) return null;

        return InvoiceSummary.builder()
                .invoiceId(invoice.getId())
                .method(invoice.getMethod() != null ? invoice.getMethod().name() : null)
                .status(invoice.getStatus().name())
                .paidAt(invoice.getPaidAt())
                .build();
    }


}