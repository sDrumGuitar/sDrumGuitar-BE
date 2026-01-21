package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class CreateStudentResponse {
    @JsonProperty("student_id")
    private Long studentId;

    private String name;

    @JsonProperty("age_group")
    private String ageGroup;

    private String phone;

    @JsonProperty("parent_phone")
    private String parentPhone;

    private String memo;

    @JsonProperty("family_discount")
    private boolean familyDiscount;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
}
