package teamDBMS.sDrumGuitarBE.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.course.dto.AllCourseResponse;
import teamDBMS.sDrumGuitarBE.course.dto.CoursePageResponse;
import teamDBMS.sDrumGuitarBE.course.dto.CreateCourseRequest;
import teamDBMS.sDrumGuitarBE.course.dto.CourseResponse;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.course.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    // 코스 생성
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest req) {
        CourseResponse res = courseService.createCourse(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res); // 201
    }

    @GetMapping
    public ResponseEntity<CoursePageResponse> getAllCourses(
            @RequestParam(required = false) Course.EnrollmentStatus status,
            @RequestParam(required = false) Course.ClassType classType,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
                courseService.getCourses(status, classType, studentName, year, month, page, size)
        );
    }
}
