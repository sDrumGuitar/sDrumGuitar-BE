package teamDBMS.sDrumGuitarBE.invoice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: enrollment_id (UNIQUE) => 1:1
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false, unique = true)
    private Course course;

    // FK: student_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // 생성일(issued_at)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant issuedAt;

    // 결제일(paid_at)
    private Instant paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private InvoiceStatus status = InvoiceStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod method;

    @Lob
    @Column(columnDefinition = "text")
    private String memo;

    public enum InvoiceStatus {
        UNPAID, PAID
    }

    public enum PaymentMethod {
        CARD, CASH
    }

    public void markPaid(Invoice.PaymentMethod method, Instant paidAt) {
        this.status = Invoice.InvoiceStatus.PAID;
        this.method = method;
        this.paidAt = paidAt;
    }
}
