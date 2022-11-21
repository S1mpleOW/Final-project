package s1mple.dlowji.ffms_refactor.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SignInForm {
	@NotNull(message = "Please enter username")
	@NotEmpty(message = "Please enter username")
	private String username;

	@NotNull(message = "Please enter password")
	@NotEmpty(message = "Please enter password")
	private String password;
}
