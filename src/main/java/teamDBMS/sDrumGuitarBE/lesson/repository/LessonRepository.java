package teamDBMS.sDrumGuitarBE.lesson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @EntityGraph(attributePaths = {"course", "course.student"})
    List<Lesson> findAllByStartAtGreaterThanEqualAndStartAtLessThan(
            LocalDateTime from,
            LocalDateTime to
    );
}
