package teamDBMS.sDrumGuitarBE.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RolloverLesson {

    private Long lessonId;
    private Long studentId;
    private String name;
    private String parentPhone;

    private Course.ClassType classType;
    private Course.EnrollmentStatus courseStatus;

    private Lesson.AttendanceStatus attendanceStatus;
    private Lesson.LessonTag lessonTag;

    private LocalDateTime startAt;
    private LocalDateTime beforeAt;
    private int remainingLessons;
    private LocalDateTime updatedAt;
}
