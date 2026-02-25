package teamDBMS.sDrumGuitarBE.lesson.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.course.repository.CourseRepository;
import teamDBMS.sDrumGuitarBE.lesson.dto.*;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;
import teamDBMS.sDrumGuitarBE.lesson.repository.LessonRepository;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleRequest;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.student.entity.Student;

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
    private final CourseRepository courseRepository;

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
    
    @Transactional
    public LessonAttendanceResponse updateAttendance(Long lessonId, UpdateLessonAttendanceRequest req) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("lesson not found: " + lessonId));

        Lesson.AttendanceStatus newStatus = req.toAttendanceStatus();

        // 허용 값 체크(혹시 enum에 다른 값이 있으면 방어)
        if (newStatus != Lesson.AttendanceStatus.ATTENDED
                && newStatus != Lesson.AttendanceStatus.ABSENT
                && newStatus != Lesson.AttendanceStatus.ROLLOVER) {
            throw new IllegalArgumentException("invalid attendance_status: " + req.getAttendanceStatus());
        }

        lesson.updateAttendance(newStatus);

        // JPA dirty checking으로 저장됨 (굳이 save 안 해도 됨)
        // lessonRepository.save(lesson);

        return LessonAttendanceResponse.from(lesson);
    }

    @Transactional
    public MakeupLessonResponse makeUpLesson(Long lessonId, MakeupLessonRequest request) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));

        lesson.makeUp(request.getMakeupStartAt());

        return MakeupLessonResponse.builder()
                .lessonId(lesson.getId())
                .attendanceStatus(lesson.getAttendanceStatus())
                .lessonTag(lesson.getLessonTag())
                .startAt(lesson.getStartAt())
                .beforeAt(lesson.getBeforeAt())
                .updatedAt(lesson.getUpdatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public int remainingLessons(Long studentId) {

        // 1. 최근 ACTIVE course 조회
        Course course = courseRepository
                .findTopByStudentIdAndStatusOrderByCreatedAtDesc(
                        studentId,
                        Course.EnrollmentStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Active course not found")
                );

        // 2. 차감 대상 출결 상태
        List<Lesson.AttendanceStatus> deductedStatuses =
                List.of(
                        Lesson.AttendanceStatus.ATTENDED,
                        Lesson.AttendanceStatus.ABSENT
                );

        // 3. 사용된 회차 수
        long usedCount = lessonRepository
                .countByCourseIdAndAttendanceStatusIn(
                        course.getId(),
                        deductedStatuses
                );

        // 4. 남은 회차 계산
        return course.getLessonCount() - (int) usedCount;
    }

    @Transactional(readOnly = true)
    public List<RolloverLesson> getRolloverLessons(
            Integer year,
            Integer month,
            Long studentId,
            Course.ClassType classType
    ) {

        List<Lesson> lessons = lessonRepository.findAllRolloverLessons(
                year, month, studentId, classType
        );

        return lessons.stream()
                .map(lesson -> {

                    Course course = lesson.getCourse();
                    Long sid = course.getStudent().getId();

                    int remaining = remainingLessons(sid);

                    return new RolloverLesson(
                            lesson.getId(),
                            sid,
                            course.getStudent().getName(),
                            course.getStudent().getParentPhone(),
                            course.getClassType(),
                            course.getStatus(),
                            lesson.getAttendanceStatus(),
                            lesson.getLessonTag(),
                            lesson.getBeforeAt(),
                            remaining,
                            lesson.getUpdatedAt()
                    );
                })
                .toList();
    }

    @Transactional
    public CreateRolloverLessonResponse createRolloverLesson(
            Long lessonId,
            CreateRolloverLessonRequest request
    ) {

        Lesson origin = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Lesson not found")
                );

        // 1️⃣ 이월 상태 체크
        if (origin.getAttendanceStatus() != Lesson.AttendanceStatus.ROLLOVER
                || origin.getLessonTag() != Lesson.LessonTag.NORMAL) {
            throw new IllegalStateException(
                    "Only rollover lessons can create a rollover session"
            );
        }

        // 2️⃣ 이미 후속 일정 있는지 체크
        if (lessonRepository.existsByOriginLessonId(origin.getId())) {
            throw new IllegalStateException(
                    "Rollover lesson already has a follow-up session"
            );
        }

        // 3️⃣ 기존 lesson 상태 변경
        origin.markAsRolloverConfirmed();

        // 4️⃣ 새 lesson 생성
        Lesson newLesson = Lesson.builder()
                .course(origin.getCourse())
                .startAt(request.getStartAt())
                .beforeAt(origin.getStartAt())
                .lessonTag(Lesson.LessonTag.NORMAL)
                .attendanceStatus(Lesson.AttendanceStatus.NOTYET)
                .originLesson(origin)
                .build();

        lessonRepository.save(newLesson);

        return new CreateRolloverLessonResponse(
                new CreateRolloverLessonResponse.OriginLesson(
                        origin.getId(),
                        origin.getLessonTag(),
                        origin.getAttendanceStatus()
                ),
                new CreateRolloverLessonResponse.NewLesson(
                        newLesson.getId(),
                        newLesson.getLessonTag(),
                        newLesson.getAttendanceStatus(),
                        newLesson.getStartAt(),
                        newLesson.getBeforeAt()
                )
        );
    }


}
