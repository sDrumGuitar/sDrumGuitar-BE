package teamDBMS.sDrumGuitarBE.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
