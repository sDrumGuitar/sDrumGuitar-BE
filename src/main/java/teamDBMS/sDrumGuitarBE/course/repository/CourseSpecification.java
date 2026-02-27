package teamDBMS.sDrumGuitarBE.course.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import teamDBMS.sDrumGuitarBE.course.entity.Course;

public class CourseSpecification {

    public static Specification<Course> filter(
            Course.EnrollmentStatus status,
            Course.ClassType classType,
            String studentName,
            Integer year,
            Integer month
    ) {
        return (root, query, cb) -> {

            if (query.getResultType() != Long.class) {
                root.fetch("student", JoinType.LEFT);
                root.fetch("schedules", JoinType.LEFT);
                root.fetch("invoice", JoinType.LEFT);
                query.distinct(true);
            }

            Predicate predicate = cb.conjunction();

            if (status != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("status"), status));
            }

            if (classType != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("classType"), classType));
            }

            if (studentName != null) {
                predicate = cb.and(predicate,
                        cb.like(
                                cb.lower(root.get("student").get("name")),
                                "%" + studentName.toLowerCase() + "%"
                        ));
            }

            if (year != null) {
                predicate = cb.and(predicate,
                        cb.equal(
                                cb.function("year", Integer.class, root.get("startDate")),
                                year));
            }

            if (month != null) {
                predicate = cb.and(predicate,
                        cb.equal(
                                cb.function("month", Integer.class, root.get("startDate")),
                                month));
            }

            return predicate;
        };
    }
}
