package teamDBMS.sDrumGuitarBE.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceSummary;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleResponse;
import teamDBMS.sDrumGuitarBE.student.dto.StudentSummary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class AllCourseResponse {

    private Long courseId;
    private StudentSummary student;
    private String classType;
    private LocalDate startDate;
    private String status;
    private int lessonCount;
    private List<ScheduleResponse> schedules;
    private InvoiceSummary invoice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static AllCourseResponse from(Course course) {
        return AllCourseResponse.builder()
                .courseId(course.getId())
                .student(StudentSummary.from(course.getStudent()))
                .classType(course.getClassType().name())
                .startDate(course.getStartDate())
                .status(course.getStatus().name())
                .lessonCount(course.getLessonCount())
                .schedules(
                        course.getSchedules()
                                .stream()
                                .map(ScheduleResponse::from)
                                .toList()
                )
                .invoice(InvoiceSummary.from(course.getInvoice()))
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}