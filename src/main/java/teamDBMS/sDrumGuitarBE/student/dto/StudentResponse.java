package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.LocalDateTime;

@Getter
@Builder
public class StudentResponse {

    private Long studentId;

    private String name;

    private String ageGroup;

    private String phone;

    private String parentPhone;

    private String memo;

    private boolean familyDiscount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static StudentResponse from(Student student) {
        return StudentResponse.builder()
                .studentId(student.getId())
                .name(student.getName())
                .ageGroup(student.getAgeGroup().name())
                .phone(student.getPhone())
                .parentPhone(student.getParentPhone())
                .memo(student.getMemo())
                .familyDiscount(student.isFamilyDiscount())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
