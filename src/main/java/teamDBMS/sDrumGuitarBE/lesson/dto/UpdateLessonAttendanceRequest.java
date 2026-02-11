package teamDBMS.sDrumGuitarBE.lesson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamDBMS.sDrumGuitarBE.lesson.entity.Lesson;

@Getter
@NoArgsConstructor
public class UpdateLessonAttendanceRequest {

    @NotBlank
    @JsonProperty("attendance_status")
    private String attendanceStatus;

    public Lesson.AttendanceStatus toAttendanceStatus() {
        // "attended" / "ABSENT" / "Rollover" 다 받게
        return Lesson.AttendanceStatus.valueOf(attendanceStatus.trim().toUpperCase());
    }
}
