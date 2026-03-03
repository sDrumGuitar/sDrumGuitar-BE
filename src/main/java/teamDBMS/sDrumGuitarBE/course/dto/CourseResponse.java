package teamDBMS.sDrumGuitarBE.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceResponse;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleResponse;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

import java.time.Instant;
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
    private Course.ClassType classType;

    private Course.EnrollmentStatus status;

    @JsonProperty("lesson_count")
    private Integer lessonCount;

    @JsonProperty("start_date")
    private LocalDate startDate;

    private List<ScheduleResponse> schedules;

    private InvoiceResponse invoice;

    @JsonProperty("created_at")
    private Instant createdAt;

    public static CourseResponse from(Course course) {
        return CourseResponse.builder()
                .courseId(course.getId())
                .studentId(course.getStudent().getId())
                .classType(course.getClassType())
                .status(course.getStatus())
                .lessonCount(course.getLessonCount())
                .startDate(course.getStartDate())
                .createdAt(course.getCreatedAt())
                .invoice(
                        course.getInvoice() != null
                                ? InvoiceResponse.from(course.getInvoice())
                                : null
                )
                .schedules(
                        course.getSchedules() != null
                                ? course.getSchedules()
                                .stream()
                                .map(ScheduleResponse::from)
                                .toList()
                                : List.of()
                )
                .build();
    }
}
