package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

@Getter
@NoArgsConstructor
public class CreateStudentRequest {

    @NotBlank
    @Size(max = 10)
    private String name;

    @NotNull
    @JsonProperty("age_group")
    private Student.AgeGroup ageGroup;

    @Size(max = 20)
    private String phone;

    @JsonProperty("parent_phone")
    @Size(max = 20)
    private String parentPhone;

    private String memo;

    @AssertTrue(message = "phone or parent_phone is required")
    @JsonIgnore
    public boolean isPhoneOrParentPhonePresent() {
        boolean hasPhone = phone != null && !phone.isBlank();
        boolean hasParent = parentPhone != null && !parentPhone.isBlank();
        return hasPhone || hasParent;
    }

    public Student toEntity() {
        return Student.builder()
                .name(name)
                .ageGroup(ageGroup)
                .phone(phone)
                .parentPhone(parentPhone)
                .memo(memo)
                .build();
    }

}

