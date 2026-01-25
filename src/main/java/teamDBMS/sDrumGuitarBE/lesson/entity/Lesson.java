package teamDBMS.sDrumGuitarBE.lesson.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Lesson extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("FK: course_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Comment("수업 시작 시간")
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Comment("세션 태그: normal/makeup/rollover")
    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_tag", nullable = false)
    @Builder.Default
    private LessonTag lessonTag = LessonTag.NORMAL;

    @Comment("출결 상태: notyet/attended/absent/makeup/rollover")
    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status", nullable = false)
    @Builder.Default
    private AttendanceStatus attendanceStatus = AttendanceStatus.NOTYET;

    @Comment("원래 수업 날짜(변경 시 들어감). null이면 start_at로 자동 세팅")
    @Column(name = "before_at", nullable = false)
    private LocalDateTime beforeAt;

    @PrePersist
    private void prePersist() {
        // DB 스펙이 before_at NOT NULL 이라서 안전장치
        if (beforeAt == null) {
            beforeAt = startAt;
        }
        if (lessonTag == null) {
            lessonTag = LessonTag.NORMAL;
        }
        if (attendanceStatus == null) {
            attendanceStatus = AttendanceStatus.NOTYET;
        }
    }

    public enum LessonTag {
        NORMAL, MAKEUP, ROLLOVER
    }

    public enum AttendanceStatus {
        NOTYET, ATTENDED, ABSENT, MAKEUP, ROLLOVER
    }
}
