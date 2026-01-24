package teamDBMS.sDrumGuitarBE.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: course_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Weekday weekday;

    @Column(nullable = false)
    private LocalTime time;

    public enum Weekday {
        MON, TUE, WED, THU, FRI, SAT, SUN
    }
}
