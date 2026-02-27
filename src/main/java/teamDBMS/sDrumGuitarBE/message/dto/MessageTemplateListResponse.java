package teamDBMS.sDrumGuitarBE.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MessageTemplateListResponse {

    private long totalCount;
    private int page;
    private int size;
    private List<MessageTemplateResponse> templates;

}
