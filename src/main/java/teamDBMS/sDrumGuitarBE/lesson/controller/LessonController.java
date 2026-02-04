package teamDBMS.sDrumGuitarBE.lesson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.lesson.dto.MonthlyLessonsResponse;
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
}
