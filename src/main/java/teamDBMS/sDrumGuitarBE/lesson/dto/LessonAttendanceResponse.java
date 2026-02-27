package teamDBMS.sDrumGuitarBE.lesson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.Instant;

@Getter
@Builder
public class LessonAttendanceResponse {

    @JsonProperty("lesson_id")
    private Long lessonId;

    @JsonProperty("attendance_status")
    private String attendanceStatus;

    @JsonProperty("lesson_tag")
    private String lessonTag;

    @JsonProperty("start_at")
    private Instant startAt;

    @JsonProperty("before_at")
    private Instant beforeAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public static LessonAttendanceResponse from(Lesson lesson) {
        return LessonAttendanceResponse.builder()
                .lessonId(lesson.getId())
                .attendanceStatus(lesson.getAttendanceStatus().name().toLowerCase())
                .lessonTag(lesson.getLessonTag().name().toLowerCase())
                .startAt(lesson.getStartAt())
                .beforeAt(lesson.getBeforeAt())
                .updatedAt(lesson.getUpdatedAt()) // BaseEntity에 updatedAt 있다고 가정
                .build();
    }
}
