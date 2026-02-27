package teamDBMS.sDrumGuitarBE.lesson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MakeupLessonRequest {

    @NotNull(message = "makeup_start_at is required")
    @JsonProperty("makeup_start_at")
    private Instant makeupStartAt;
}
