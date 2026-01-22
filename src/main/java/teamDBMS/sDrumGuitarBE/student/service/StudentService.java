package teamDBMS.sDrumGuitarBE.student.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentRequest;
import teamDBMS.sDrumGuitarBE.student.dto.CreateStudentResponse;
import teamDBMS.sDrumGuitarBE.student.entity.Student;
import teamDBMS.sDrumGuitarBE.student.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @Transactional
    public CreateStudentResponse create(CreateStudentRequest request) {
        Student student = request.toEntity();
        Student saved = studentRepository.save(student);

        return CreateStudentResponse.from(saved);
    }


}
