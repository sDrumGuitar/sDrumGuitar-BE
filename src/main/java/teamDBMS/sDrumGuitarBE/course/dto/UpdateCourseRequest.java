package teamDBMS.sDrumGuitarBE.course.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceUpdateRequest;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateCourseRequest {

    @JsonProperty("class_type")
    private Course.ClassType classType;

    @JsonProperty("lesson_count")
    private Integer lessonCount;

    private Course.EnrollmentStatus status;

    @JsonProperty("start_date")
    private Instant startDate;

    private List<ScheduleRequest> schedules;

    private InvoiceUpdateRequest invoice;

    @JsonIgnore
    public boolean isEmpty() {
        return classType == null &&
                lessonCount == null &&
                status == null &&
                startDate == null &&
                schedules == null &&
                invoice == null;
    }
}