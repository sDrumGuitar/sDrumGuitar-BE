package teamDBMS.sDrumGuitarBE.lesson.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MonthlyLessonsResponse {
    private int year;
    private int month;
    private List<DayLessons> days;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class DayLessons {
        private LocalDate date;           // "2026-11-03"
        private List<LessonItem> lessons; // 해당 날짜의 lesson 목록
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class LessonItem {
        private Long lessonId;
        private String name;              // 학생 이름
        private String classType;         // "DRUM"
        private String courseStatus;      // "active" (소문자)
        private String lessonTag;         // "normal" (소문자)
        private String attendanceStatus;  // "notyet" (소문자)
        private String startAt;           // "2026-11-03T15:00:00"
    }
}

