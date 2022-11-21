package s1mple.dlowji.ffms_refactor.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BookedTicketForm {
    @NotNull(message = "Field id must not null")
    private Long field_id;
}
