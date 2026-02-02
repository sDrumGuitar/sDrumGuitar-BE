package teamDBMS.sDrumGuitarBE.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScheduleRequest {

    private Course course;
    private Schedule.Weekday weekday;
    private LocalTime time;

    public Schedule toEntity(Course course) {
        return Schedule.builder()
                .course(course)
                .weekday(weekday)
                .time(time)
                .build();
    }
}
