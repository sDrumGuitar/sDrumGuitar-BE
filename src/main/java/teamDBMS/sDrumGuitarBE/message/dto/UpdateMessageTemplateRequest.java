package teamDBMS.sDrumGuitarBE.message.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.message.entity.MessageType;

@Getter
@NoArgsConstructor
public class UpdateMessageTemplateRequest {

    private MessageType type;

    @Size(max = 50)
    private String title;

    private String content;

    @JsonIgnore
    public boolean isEmpty() {
        return type == null && title == null && content == null;
    }
}