package teamDBMS.sDrumGuitarBE.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateRolloverLessonResponse {

    private OriginLesson originLesson;
    private NewLesson newLesson;

    @Getter
    @AllArgsConstructor
    public static class OriginLesson {
        private Long lessonId;
        private Lesson.LessonTag lessonTag;
        private Lesson.AttendanceStatus attendanceStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class NewLesson {
        private Long lessonId;
        private Lesson.LessonTag lessonTag;
        private Lesson.AttendanceStatus attendanceStatus;
        private Instant startAt;
        private Instant beforeAt;
    }
}