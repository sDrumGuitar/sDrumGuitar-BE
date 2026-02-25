package teamDBMS.sDrumGuitarBE.lesson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateRolloverLessonRequest {

    @NotNull(message = "start_at is required")
    @JsonProperty("start_at")
    private LocalDateTime startAt;
}