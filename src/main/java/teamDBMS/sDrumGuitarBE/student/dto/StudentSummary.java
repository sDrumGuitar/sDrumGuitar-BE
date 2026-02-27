package teamDBMS.sDrumGuitarBE.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

@Getter
@Builder
@AllArgsConstructor
public class StudentSummary {
    private Long studentId;
    private String name;
    private String ageGroup;
    private String phone;
    private String parentPhone;

    public static StudentSummary from(Student student) {
        return StudentSummary.builder()
                .studentId(student.getId())
                .name(student.getName())
                .ageGroup(student.getAgeGroup().name())
                .phone(student.getPhone())
                .parentPhone(student.getParentPhone())
                .build();
    }
}