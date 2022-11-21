package s1mple.dlowji.ffms_refactor.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class TokenDTO {
	@NotNull(message = "Username must not be null")
	@NotEmpty(message = "Please enter username")
	private String username;
}
