package teamDBMS.sDrumGuitarBE.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

        Optional<Course> findTopByStudentIdAndStatusOrderByCreatedAtDesc(
                Long studentId,
                Course.EnrollmentStatus status
        );

}
