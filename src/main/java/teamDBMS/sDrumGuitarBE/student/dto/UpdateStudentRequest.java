package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

@Getter
@NoArgsConstructor
public class UpdateStudentRequest {

    @Size(max = 10)
    private String name;

    @JsonProperty("age_group")
    private Student.AgeGroup ageGroup;

    @Size(max = 20)
    private String phone;

    @JsonProperty("parent_phone")
    @Size(max = 20)
    private String parentPhone;

    private String memo;
}