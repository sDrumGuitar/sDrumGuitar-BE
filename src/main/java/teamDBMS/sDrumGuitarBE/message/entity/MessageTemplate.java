package teamDBMS.sDrumGuitarBE.message.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public MessageTemplate(MessageType type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public void update(
            MessageType type,
            String title,
            String content
    ) {

        if (type != null) {
            this.type = type;
        }

        if (title != null) {
            this.title = title;
        }

        if (content != null) {
            this.content = content; // 치환되지 않은 원본 그대로 저장
        }
    }

}