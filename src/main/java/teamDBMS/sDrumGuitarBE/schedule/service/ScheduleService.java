package teamDBMS.sDrumGuitarBE.schedule.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleResponse;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public List<Schedule> createSchedules(Course course, List<ScheduleRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();

        List<Schedule> schedules = reqs.stream()
                .map(r -> r.toEntity(course))
                .toList();

        return scheduleRepository.saveAll(schedules);
    }


    public ScheduleResponse toResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .weekday(schedule.getWeekday())
                .time(schedule.getTime())
                .build();
    }
}
