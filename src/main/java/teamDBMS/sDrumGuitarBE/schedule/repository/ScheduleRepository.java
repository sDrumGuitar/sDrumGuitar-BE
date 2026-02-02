package teamDBMS.sDrumGuitarBE.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}