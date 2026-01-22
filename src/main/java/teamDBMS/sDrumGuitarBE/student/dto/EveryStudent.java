package teamDBMS.sDrumGuitarBE.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EveryStudent {

    private long totalCount;

    private int page;
    private int size;

    private List<StudentResponse> students;
}
