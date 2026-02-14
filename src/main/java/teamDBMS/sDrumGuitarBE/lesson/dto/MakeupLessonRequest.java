package teamDBMS.sDrumGuitarBE.lesson.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MakeupLessonRequest {

    @NotNull(message = "makeup_start_at is required")
    private LocalDateTime makeupStartAt;
}
