package teamDBMS.sDrumGuitarBE.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamDBMS.sDrumGuitarBE.course.dto.CreateCourseRequest;
import teamDBMS.sDrumGuitarBE.course.dto.CourseResponse;
import teamDBMS.sDrumGuitarBE.course.entity.Course;
import teamDBMS.sDrumGuitarBE.course.repository.CourseRepository;
import teamDBMS.sDrumGuitarBE.invoice.dto.CreateInvoiceRequest;
import teamDBMS.sDrumGuitarBE.invoice.dto.InvoiceResponse;
import teamDBMS.sDrumGuitarBE.invoice.entity.Invoice;
import teamDBMS.sDrumGuitarBE.invoice.service.InvoiceSerive;
import teamDBMS.sDrumGuitarBE.schedule.dto.ScheduleResponse;
import teamDBMS.sDrumGuitarBE.schedule.entity.Schedule;
import teamDBMS.sDrumGuitarBE.schedule.service.ScheduleService;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final InvoiceSerive invoiceService;

    private final ScheduleService scheduleService;

    @Transactional
    public CourseResponse createCourse(CreateCourseRequest req) {
        // 1) 학생 조회
        Student student = studentRepository.findById(req.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + req.getStudentId()));

        // 2) Course 생성/저장
        Course course = Course.builder()
                .student(student)
                .classType(Course.ClassType.valueOf(req.getClassType().name()))
                //.familyDiscount(req.getFamilyDiscount())
                .lessonCount(req.getLessonCount())
                .startDate(req.getStartDate())
                .build();

        courseRepository.save(course);

        List<Schedule> schedules = scheduleService.createSchedules(course, req.getSchedules());

        CreateInvoiceRequest invReq = req.getInvoice();
        Invoice invoice = invoiceService.createInvoice(course, student, invReq);

        // 5) Response 반환
        return CourseResponse.from(course, schedules, invoice);
    }
}

