package teamDBMS.sDrumGuitarBE.lesson.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.lesson.dto.MonthlyLessonsResponse;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;
import teamDBMS.sDrumGuitarBE.lesson.repository.LessonRepository;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    @Transactional
    public List<Lesson> generateLessons(
            Course course,
            LocalDate startDate,
            int lessonCount,
            List<ScheduleRequest> schedules) {
        record Slot(LocalDateTime dateTime, Schedule.Weekday weekday, LocalTime time) {}

        PriorityQueue<Slot> pq = new PriorityQueue<>(Comparator.comparing(Slot::dateTime));

        // 각 스케줄별 "첫 번째 발생" 계산해서 PQ에 넣기
        for (ScheduleRequest s : schedules) {
            DayOfWeek dow = toDayOfWeek(s.getWeekday());

            LocalDate firstDate = startDate.with(TemporalAdjusters.nextOrSame(dow));
            LocalDateTime firstDateTime = LocalDateTime.of(firstDate, s.getTime());

            pq.add(new Slot(firstDateTime, s.getWeekday(), s.getTime()));
        }

        // PQ에서 가장 빠른 것부터 꺼내며 lessonCount개 채우기
        List<Lesson> result = new java.util.ArrayList<>(lessonCount);

        for (int i = 0; i < lessonCount; i++) {
            Slot cur = pq.poll();
            if (cur == null) break;

            result.add(Lesson.builder()
                    .course(course)
                    .startAt(cur.dateTime())
                    // lessonTag/attendanceStatus/beforeAt은 엔티티 @PrePersist로 기본값/세팅
                    .build()
            );

            // 다음 주 같은 요일/시간으로 다시 넣기
            pq.add(new Slot(cur.dateTime().plusWeeks(1), cur.weekday(), cur.time()));
        }
        return result;
    }

    private DayOfWeek toDayOfWeek(Schedule.Weekday weekday) {
        return switch (weekday) {
            case MON -> DayOfWeek.MONDAY;
            case TUE -> DayOfWeek.TUESDAY;
            case WED -> DayOfWeek.WEDNESDAY;
            case THU -> DayOfWeek.THURSDAY;
            case FRI -> DayOfWeek.FRIDAY;
            case SAT -> DayOfWeek.SATURDAY;
            case SUN -> DayOfWeek.SUNDAY;
        };
    }

    @Transactional(readOnly = true)
    public MonthlyLessonsResponse getMonthlyLessons(int year, int month) {

        YearMonth ym = YearMonth.of(year, month);

        // [monthStart, nextMonthStart)
        LocalDateTime from = ym.atDay(1).atStartOfDay();
        LocalDateTime to = ym.plusMonths(1).atDay(1).atStartOfDay();

        // beforeAt 기준으로 월에 속하는 lesson 조회
        List<Lesson> lessons = lessonRepository
                .findAllByStartAtGreaterThanEqualAndStartAtLessThan(from, to);

        Map<LocalDate, List<Lesson>> grouped = lessons.stream()
                .collect(Collectors.groupingBy(l -> l.getStartAt().toLocalDate()));


        // date 오름차순 정렬 + 각 date 내부 lessons도 시간순 정렬(원하면 startAt 기준)
        List<MonthlyLessonsResponse.DayLessons> days = grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> {
                    LocalDate date = e.getKey();
                    List<MonthlyLessonsResponse.LessonItem> items = e.getValue().stream()
                            .sorted(Comparator.comparing(Lesson::getStartAt)) // 실제 수업시간 순
                            .map(this::toItem)
                            .toList();

                    return MonthlyLessonsResponse.DayLessons.builder()
                            .date(date)
                            .lessons(items)
                            .build();
                })
                .toList();

        return MonthlyLessonsResponse.builder()
                .year(year)
                .month(month)
                .days(days)
                .build();
    }

    private MonthlyLessonsResponse.LessonItem toItem(Lesson l) {
        return MonthlyLessonsResponse.LessonItem.builder()
                .lessonId(l.getId())
                .name(l.getCourse().getStudent().getName())
                .classType(l.getCourse().getClassType().name()) // "DRUM"
                .courseStatus(l.getCourse().getStatus().name().toLowerCase()) // "active"
                .lessonTag(l.getLessonTag().name().toLowerCase()) // "normal"
                .attendanceStatus(l.getAttendanceStatus().name().toLowerCase()) // "notyet"
                .startAt(l.getStartAt().toString()) // "2026-11-03T15:00"
                .build();
    }
}
