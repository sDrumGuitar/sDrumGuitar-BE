package teamDBMS.sDrumGuitarBE.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: student_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ClassType classType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(nullable = false)
    private Integer lessonCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(mappedBy = "course", fetch = FetchType.LAZY)
    private Invoice invoice;

    public enum ClassType {
        DRUM, GUITAR
    }

    public enum EnrollmentStatus {
        ACTIVE, PAUSED, ENDED
    }
}