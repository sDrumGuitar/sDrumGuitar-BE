package teamDBMS.sDrumGuitarBE.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentRequest;
import teamDBMS.sDrumGuitarBE.student.dto.EveryStudent;
import teamDBMS.sDrumGuitarBE.student.dto.StudentResponse;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<StudentResponse> createStudent(
            @RequestBody CreateStudentRequest createStudentRequest
    ) {
        StudentResponse response = studentService.create(createStudentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public EveryStudent getStudents(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size); // Spring은 0-base
        Page<Student> result = studentService.getStudents(pageable);

        List<StudentResponse> students = result.getContent().stream()
                .map(StudentResponse::from)
                .toList();

        return EveryStudent.builder()
                .totalCount(result.getTotalElements())
                .page(page)
                .size(size)
                .students(students)
                .build();
    }


}
