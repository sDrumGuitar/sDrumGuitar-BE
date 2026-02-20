package teamDBMS.sDrumGuitarBE.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RolloverLessonListResponse {

    private int totalCount;
    private List<RolloverLesson> lessons;
}
