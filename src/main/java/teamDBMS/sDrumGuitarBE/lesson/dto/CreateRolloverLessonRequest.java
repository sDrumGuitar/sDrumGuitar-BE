package teamDBMS.sDrumGuitarBE.lesson.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateRolloverLessonRequest {

    @NotNull(message = "start_at is required")
    private LocalDateTime startAt;
}