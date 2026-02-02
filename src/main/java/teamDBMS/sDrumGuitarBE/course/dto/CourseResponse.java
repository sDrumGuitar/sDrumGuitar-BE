package teamDBMS.sDrumGuitarBE.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceResponse;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleResponse;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CourseResponse {

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("class_type")
    private String classType;

    private String status;

    @JsonProperty("lesson_count")
    private Integer lessonCount;

    @JsonProperty("start_date")
    private LocalDate startDate;

    private List<ScheduleResponse> schedules;

    private InvoiceResponse invoice;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static CourseResponse from(Course course, List<Schedule> schedules, Invoice invoice) {
        return  CourseResponse.builder()
                .courseId(course.getId())
                .invoice(InvoiceResponse.from(invoice))
                .schedules(schedules.stream().map(ScheduleResponse::from).toList())
                .build();
    }
}
