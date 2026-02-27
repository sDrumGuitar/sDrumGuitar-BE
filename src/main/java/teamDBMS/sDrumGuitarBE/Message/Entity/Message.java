package teamDBMS.sDrumGuitarBE.Message.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.awt.*;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 학생에게 보냈는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Student student;

    // 어떤 템플릿 기반인지 (nullable 허용)
    @ManyToOne(fetch = FetchType.LAZY)
    private MessageTemplate template;

    // 문자 종류 (출석/지각/결석/미납 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Column(nullable = false)
    private String receiverPhone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Instant sendAt;

    @Enumerated(EnumType.STRING)
    private MessageSendStatus status;

}
