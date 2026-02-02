package teamDBMS.sDrumGuitarBE.student.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("student not found. id=" + id);
    }
}

