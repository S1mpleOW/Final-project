package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {
	private String message;
	private int status;
}
