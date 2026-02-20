package teamDBMS.sDrumGuitarBE.lesson.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.lesson.dto.RolloverLesson;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @EntityGraph(attributePaths = {"course", "course.student"})
    List<Lesson> findAllByStartAtGreaterThanEqualAndStartAtLessThan(
            LocalDateTime from,
            LocalDateTime to
    );

    long countByCourseIdAndAttendanceStatusIn(
            Long courseId,
            List<Lesson.AttendanceStatus> statuses
    );

    @Query("""
SELECT new teamDBMS.sDrumGuitarBE.lesson.dto.RolloverLesson(
    l.id,
    s.id,
    s.name,
    s.parentPhone,
    c.classType,
    c.status,
    l.attendanceStatus,
    l.lessonTag,
    l.beforeAt,
    0,
    l.updatedAt
)
FROM Lesson l
JOIN l.course c
JOIN c.student s
WHERE l.attendanceStatus = teamDBMS.sDrumGuitarBE.lesson.entity.Lesson.AttendanceStatus.ROLLOVER
AND l.lessonTag = teamDBMS.sDrumGuitarBE.lesson.entity.Lesson.LessonTag.NORMAL
""")
    List<RolloverLesson> findRolloverLessons();

    @Query("""
SELECT l
FROM Lesson l
JOIN FETCH l.course c
JOIN FETCH c.student s
WHERE l.attendanceStatus = 'ROLLOVER'
AND l.lessonTag = 'NORMAL'
AND (:studentId IS NULL OR s.id = :studentId)
AND (:classType IS NULL OR c.classType = :classType)
AND (:year IS NULL OR FUNCTION('YEAR', l.startAt) = :year)
AND (:month IS NULL OR FUNCTION('MONTH', l.startAt) = :month)
""")
    List<Lesson> findAllRolloverLessons(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("studentId") Long studentId,
            @Param("classType") Course.ClassType classType
    );




}
