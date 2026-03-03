package teamDBMS.sDrumGuitarBE.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.Instant;
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
    @Builder.Default
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "course",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Invoice invoice;

    public enum ClassType {
        DRUM, GUITAR
    }

    public enum EnrollmentStatus {
        ACTIVE, PAUSED, ENDED
    }

    public void replaceSchedules(List<Schedule> newSchedules) {
        this.schedules.clear();
        this.schedules.addAll(newSchedules);
    }

    public void changeClassType(ClassType type) {
        this.classType = type;
    }

    public void changeLessonCount(Integer count) {
        this.lessonCount = count;
    }

    public void changeStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public void changeStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}