package teamDBMS.sDrumGuitarBE.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.course.dto.CreateCourseRequest;
import teamDBMS.sDrumGuitarBE.course.dto.CourseResponse;
import teamDBMS.sDrumGuitarBE.course.service.CourseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    // 코스 생성
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest req) {
        CourseResponse res = courseService.createCourse(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res); // 201
    }
}
