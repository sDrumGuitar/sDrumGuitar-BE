package teamDBMS.sDrumGuitarBE.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class StudentInvoiceItem {

    private Long invoiceId;
    private Long enrollmentId;

    private LocalDateTime issuedAt;
    private LocalDateTime paidAt;

    private String status;
    private String method;

    private Integer lessonCount;
    private Boolean familyDiscount;
    private String classType;

    private Integer totalAmount;
}