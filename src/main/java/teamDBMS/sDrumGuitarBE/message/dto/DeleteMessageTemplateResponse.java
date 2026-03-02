package teamDBMS.sDrumGuitarBE.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMessageTemplateResponse {

    @JsonProperty("template_id")
    private Long templateId;

    private String message;
}