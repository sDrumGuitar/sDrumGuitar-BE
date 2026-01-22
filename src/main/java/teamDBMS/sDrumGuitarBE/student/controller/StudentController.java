package teamDBMS.sDrumGuitarBE.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentRequest;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentResponse;
import teamDBMS.sDrumGuitarBE.student.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    public ResponseEntity<CreateStudentResponse> createStudent(
            @RequestBody CreateStudentRequest createStudentRequest
    ) {
        CreateStudentResponse response = studentService.create(createStudentRequest);
        return ResponseEntity.ok(response);
    }


}
