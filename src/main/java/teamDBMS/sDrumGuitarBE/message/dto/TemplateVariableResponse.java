package teamDBMS.sDrumGuitarBE.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.message.entity.TemplateVariable;

@Getter
@AllArgsConstructor
public class TemplateVariableResponse {

    private String name;         // STUDENT_NAME
    private String placeholder;  // {학생 이름}

    public static TemplateVariableResponse from(TemplateVariable variable) {
        return new TemplateVariableResponse(
                variable.name(),
                variable.getKey()
        );
    }
}