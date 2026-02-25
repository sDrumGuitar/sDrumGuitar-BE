package teamDBMS.sDrumGuitarBE.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class AllCourseResponse {

    private Long courseId;
    private Long studentId;
    private String studentName;
    private Course.ClassType classType;
    private LocalDate startDate;
    private Course.EnrollmentStatus status;
    private int lessonCount;

    public static AllCourseResponse from(Course course) {
        return AllCourseResponse.builder()
                .courseId(course.getId())
                .studentId(course.getStudent().getId())
                .studentName(course.getStudent().getName())
                .classType(course.getClassType())
                .startDate(course.getStartDate())
                .status(course.getStatus())
                .lessonCount(course.getLessonCount())
                .build();
    }
}