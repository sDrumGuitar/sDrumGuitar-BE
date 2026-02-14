package teamDBMS.sDrumGuitarBE.lesson.dto;

import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson.AttendanceStatus;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson.LessonTag;

import java.time.LocalDateTime;

@Getter
@Builder
public class MakeupLessonResponse {

    private Long lessonId;
    private AttendanceStatus attendanceStatus;
    private LessonTag lessonTag;
    private LocalDateTime startAt;
    private LocalDateTime beforeAt;
    private LocalDateTime updatedAt;
}
