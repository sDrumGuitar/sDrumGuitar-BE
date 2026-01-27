package teamDBMS.sDrumGuitarBE.course.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import teamDBMS.sDrumGuitarBE.invoice.dto.CreateInvoiceRequest;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateCourseRequest {


    @NotNull
    private Long studentId;

    @NotNull
    private ClassType classType;

    @NotNull
    private Boolean familyDiscount;

    @NotNull
    @Min(1)
    private Integer lessonCount;

    @NotNull
    private LocalDate startDate;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<ScheduleRequest> schedules;

    @NotNull
    @Valid
    private CreateInvoiceRequest invoice;

    // ===== enums =====
    public enum ClassType { DRUM, GUITAR }
    public enum Weekday { MON, TUE, WED, THU, FRI, SAT, SUN }
    public enum InvoiceStatus { PAID, UNPAID }
    public enum PaymentMethod { CARD, CASH }
}
