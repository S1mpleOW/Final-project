package s1mple.dlowji.ffms_refactor.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter

public class FieldOrderForm {
	@NotNull(message = "Field id must not null")
	private Long field_id;

	@NotNull(message = "Phone number must not null")
	@Length(min = 10, max = 10 , message = "Phone number must be 10 characters")
	private String phone;

	// format date time yyyy-MM-dd'T'HH:mm:ss.SSSXXX
	@NotNull(message = "Please enter start time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime start_time;
	@NotNull(message = "Please enter end time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime end_time;
}
