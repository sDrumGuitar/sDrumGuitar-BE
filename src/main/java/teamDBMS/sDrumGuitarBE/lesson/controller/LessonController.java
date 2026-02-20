package teamDBMS.sDrumGuitarBE.lesson.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.lesson.dto.*;
import teamDBMS.sDrumGuitarBE.lesson.service.LessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public MonthlyLessonsResponse getMonthlyLessons(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        return lessonService.getMonthlyLessons(year, month);
    }

    @PatchMapping("/{lessonId}/attendance")
    public LessonAttendanceResponse updateAttendance(
            @PathVariable("lessonId")  Long lessonId,
            @Valid @RequestBody UpdateLessonAttendanceRequest req
    ) {
        return lessonService.updateAttendance(lessonId, req);
    }

    @PatchMapping("/{lessonId}/makeup")
    public ResponseEntity<MakeupLessonResponse> makeUpLesson(
            @PathVariable("lessonId")  Long lessonId,
            @Valid @RequestBody MakeupLessonRequest request
    ) {
        return ResponseEntity.ok(
                lessonService.makeUpLesson(lessonId, request)
        );
    }

    @GetMapping("/rollover")
    public ResponseEntity<RolloverLessonListResponse> getRolloverLessons(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "classType", required = false) Course.ClassType classType
    ) {

        List<RolloverLesson> list =
                lessonService.getRolloverLessons(year, month, studentId, classType);

        return ResponseEntity.ok(
                new RolloverLessonListResponse(
                        list.size(),
                        list
                )
        );
    }


}