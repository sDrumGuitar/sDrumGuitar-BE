package teamDBMS.sDrumGuitarBE.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentRequest;
import teamDBMS.sDrumGuitarBE.student.dto.EveryStudent;
import teamDBMS.sDrumGuitarBE.student.dto.StudentResponse;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponse create(CreateStudentRequest request) {
        Student student = request.toEntity();
        Student saved = studentRepository.save(student);

        return StudentResponse.from(saved);
    }

    public static EveryStudent from(Page<Student> pageResult) {
        List<StudentResponse> students = pageResult.getContent().stream()
                .map(StudentResponse::from)
                .toList();

        return EveryStudent.builder()
                .totalCount(pageResult.getTotalElements())
                .page(pageResult.getNumber() + 1) // page를 1-base로 쓰는 경우
                .size(pageResult.getSize())
                .students(students)
                .build();
    }

    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


}
