package teamDBMS.sDrumGuitarBE.lesson.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.time.Instant;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Course course;

    @Column(nullable = false)
    private Instant startAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonTag lessonTag = LessonTag.NORMAL;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus = AttendanceStatus.NOTYET;

    private Instant beforeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_lesson_id")
    private Lesson originLesson;

    public enum LessonTag {
        NORMAL, MAKEUP, ROLLOVER
    }

    public enum AttendanceStatus {
        NOTYET, ATTENDED, ABSENT, MAKEUP, ROLLOVER
    }

    public void updateAttendance(AttendanceStatus newStatus) {
        this.attendanceStatus = newStatus;

        // 스펙: 출석/결석/이월 처리 시 lesson_tag는 normal로
        this.lessonTag = LessonTag.NORMAL;

        // 스펙: start_at / before_at 변경 없음
        // this.startAt 그대로
        // this.beforeAt 그대로
    }

    public boolean isAttendanceFinalized() {
        // "이미 출결이 확정된 회차" 판단 기준:
        // NOTYET 이 아니면 이미 확정 처리된 것으로 봄
        return this.attendanceStatus != AttendanceStatus.NOTYET;
    }

    public void makeUp(Instant makeupStartAt) {

        if (this.attendanceStatus == AttendanceStatus.ATTENDED
                || this.attendanceStatus == AttendanceStatus.ABSENT) {
            throw new IllegalStateException("Attendance status already finalized");
        }

        this.beforeAt = this.startAt;
        this.startAt = makeupStartAt;
        this.lessonTag = LessonTag.MAKEUP;
        this.attendanceStatus = AttendanceStatus.NOTYET;
    }

    public void markAsRolloverConfirmed() {
        this.lessonTag = LessonTag.ROLLOVER;
        this.attendanceStatus = AttendanceStatus.ROLLOVER;
    }

}
