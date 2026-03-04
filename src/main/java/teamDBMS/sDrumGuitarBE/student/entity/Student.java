package teamDBMS.sDrumGuitarBE.student.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;

import lombok.*;
import teamDBMS.sDrumGuitarBE.student.dto.UpdateStudentRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String parentPhone;

    @Column(nullable = false)
    @Builder.Default
    private Boolean familyDiscount = false;

    @Lob
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AgeGroup ageGroup;

    public enum AgeGroup {
        PRESCHOOL, ELEMENT, MIDDLE, HIGH, ADULT;

        @JsonCreator
        public static AgeGroup from(String value) {
            if (value == null) return null;
            return AgeGroup.valueOf(value.trim().toUpperCase());
        }
    }

    public void updateStudent(UpdateStudentRequest request) {

        if (request.getName() != null) {
            this.name = request.getName();
        }

        if (request.getAgeGroup() != null) {
            this.ageGroup = request.getAgeGroup();
        }

        if (request.getPhone() != null) {
            this.phone = request.getPhone();
        }

        if (request.getParentPhone() != null) {
            this.parentPhone = request.getParentPhone();
        }

        if (request.getMemo() != null) {
            this.memo = request.getMemo();
        }
    }
}
