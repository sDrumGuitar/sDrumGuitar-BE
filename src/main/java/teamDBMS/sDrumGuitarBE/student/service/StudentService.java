package teamDBMS.sDrumGuitarBE.student.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentRequest;
import teamDBMS.sDrumGuitarBE.student.dto.EveryStudent;
import teamDBMS.sDrumGuitarBE.student.dto.StudentResponse;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.exception.StudentNotFoundException;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
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

    @Transactional(readOnly = true)
    public StudentResponse getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        return StudentResponse.from(student);
    }


}
