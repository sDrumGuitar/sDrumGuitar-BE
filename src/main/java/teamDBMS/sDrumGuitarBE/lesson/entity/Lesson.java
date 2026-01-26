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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonTag lessonTag = LessonTag.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus = AttendanceStatus.NOTYET;

    private LocalDateTime beforeAt;

    @PrePersist
    private void prePersist() {
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
