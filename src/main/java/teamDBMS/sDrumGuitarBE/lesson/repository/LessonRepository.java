package teamDBMS.sDrumGuitarBE.lesson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.lesson.dto.RolloverLesson;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @EntityGraph(attributePaths = {"course", "course.student"})
    List<Lesson> findAllByStartAtGreaterThanEqualAndStartAtLessThan(
            Instant from,
            Instant to
    );

    long countByCourseIdAndAttendanceStatusIn(
            Long courseId,
            List<Lesson.AttendanceStatus> statuses
    );

    @Query("""
SELECT l
FROM Lesson l
JOIN FETCH l.course c
JOIN FETCH c.student s
WHERE l.attendanceStatus = 'ROLLOVER'
AND l.lessonTag = 'NORMAL'
AND (:studentId IS NULL OR s.id = :studentId)
AND (:classType IS NULL OR c.classType = :classType)
AND (:from IS NULL OR l.startAt >= :from)
AND (:to IS NULL OR l.startAt < :to)
""")
    List<Lesson> findAllRolloverLessons(
            @Param("from") Instant from,
            @Param("to") Instant to,
            @Param("studentId") Long studentId,
            @Param("classType") Course.ClassType classType
    );

    boolean existsByOriginLessonId(Long originLessonId);


}
