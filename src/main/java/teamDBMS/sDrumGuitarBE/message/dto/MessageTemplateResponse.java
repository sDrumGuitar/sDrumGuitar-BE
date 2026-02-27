package teamDBMS.sDrumGuitarBE.message.dto;

import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.message.entity.MessageTemplate;
import teamDBMS.sDrumGuitarBE.message.entity.MessageType;

import java.time.Instant;

@Getter
@Builder
public class MessageTemplateResponse {

    private Long templateId;
    private MessageType type;
    private String title;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    public static MessageTemplateResponse from(MessageTemplate template) {
        return MessageTemplateResponse.builder()
                .templateId(template.getId())
                .type(template.getType())
                .title(template.getTitle())
                .content(template.getContent())
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}