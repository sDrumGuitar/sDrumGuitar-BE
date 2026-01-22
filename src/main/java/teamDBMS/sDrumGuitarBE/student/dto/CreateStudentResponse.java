package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Builder
public class CreateStudentResponse {

    @JsonProperty("student_id")
    private Long studentId;

    private String name;

    @JsonProperty("age_group")
    private String ageGroup; // "adult" 처럼 내려주려면 String이 편함

    private String phone;

    @JsonProperty("parent_phone")
    private String parentPhone;

    private String memo;

    @JsonProperty("family_discount")
    private boolean familyDiscount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static CreateStudentResponse from(Student student) {
        return CreateStudentResponse.builder()
                .studentId(student.getId())
                .name(student.getName())
                .ageGroup(student.getAgeGroup().name().toLowerCase()) // enum -> "adult"
                .phone(student.getPhone())
                .parentPhone(student.getParentPhone())
                .memo(student.getMemo())
                .familyDiscount(student.isFamilyDiscount()) // 없으면 false 고정해도 됨
                .createdAt(student.getCreatedAt())          // BaseEntity에 createdAt 있으면
                .build();
    }
}
