package teamDBMS.sDrumGuitarBE.lesson.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.lesson.dto.LessonAttendanceResponse;
import teamDBMS.sDrumGuitarBE.lesson.dto.MonthlyLessonsResponse;
import teamDBMS.sDrumGuitarBE.lesson.dto.UpdateLessonAttendanceRequest;
import teamDBMS.sDrumGuitarBE.lesson.service.LessonService;

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
            @PathVariable Long lessonId,
            @Valid @RequestBody UpdateLessonAttendanceRequest req
    ) {
        return lessonService.updateAttendance(lessonId, req);
    }
}