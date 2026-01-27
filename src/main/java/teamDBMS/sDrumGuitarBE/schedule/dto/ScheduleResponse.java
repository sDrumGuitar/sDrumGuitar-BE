package teamDBMS.sDrumGuitarBE.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

import java.time.LocalTime;

@Getter
@Builder
public class ScheduleResponse {
    private Schedule.Weekday weekday;
    private LocalTime time;

    public static ScheduleResponse from(Schedule s) {
        return ScheduleResponse.builder()
                .weekday(s.getWeekday())   // ✅ enum 그대로
                .time(s.getTime())
                .build();
    }
}

