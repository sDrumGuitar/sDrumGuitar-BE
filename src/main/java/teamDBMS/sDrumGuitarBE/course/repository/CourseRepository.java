package teamDBMS.sDrumGuitarBE.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>,
        JpaSpecificationExecutor<Course> {

        Optional<Course> findTopByStudentIdAndStatusOrderByCreatedAtDesc(
                Long studentId,
                Course.EnrollmentStatus status
        );

        @EntityGraph(attributePaths = {"student", "invoice"})
        Page<Course> findAll(Specification<Course> spec, Pageable pageable);

}
