package teamDBMS.sDrumGuitarBE.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CoursePageResponse {

    private long totalCount;
    private int page;
    private int size;
    private List<AllCourseResponse> courses;
}
