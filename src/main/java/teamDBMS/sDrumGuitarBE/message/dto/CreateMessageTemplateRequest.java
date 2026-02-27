package teamDBMS.sDrumGuitarBE.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.message.entity.MessageType;

@Getter
public class CreateMessageTemplateRequest {

    @NotNull(message = "type is required")
    private MessageType type;

    @NotBlank(message = "title is required")
    @Size(max = 50, message = "title length exceeds limit")
    private String title;

    @NotBlank(message = "content is required")
    private String content;
}