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
public class OrderMoreProductDTO {
	@NotNull(message = "Please enter id")
	private Long itemID;
	@NotNull(message = "Please enter quantity")
	private int quantity;
	@NotNull(message = "Please enter delivery date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime delivery_date;
	@NotNull(message = "Please enter note")
	private String note;
}
