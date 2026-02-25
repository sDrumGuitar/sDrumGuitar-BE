package teamDBMS.sDrumGuitarBE.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentInfoResponse {

    private Long studentId;
    private String name;
    private String phone;
    private Integer remainLessons;

}