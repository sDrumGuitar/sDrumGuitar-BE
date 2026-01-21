package teamDBMS.sDrumGuitarBE.student.entity;

import jakarta.persistence.*;
import teamDBMS.sDrumGuitarBE.common.BaseEntity;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Student extends BaseEntity{

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
    private boolean familyDiscount = false;

    @Lob
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AgeGroup ageGroup;

    public enum AgeGroup {
        PRESCHOOL, ELEMENT, MIDDLE, HIGH, ADULT
    }

    // 업데이트용 메서드 - 추후 규칙 생기면 추가
    public void updateBasicInfo(String name, AgeGroup ageGroup, String phone, String parentPhone, boolean familyDiscount, String memo) {
        this.name = name;
        this.ageGroup = ageGroup;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.familyDiscount = familyDiscount;
        this.memo = memo;
    }
}
