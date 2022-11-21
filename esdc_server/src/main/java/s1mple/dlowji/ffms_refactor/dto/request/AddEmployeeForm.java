package s1mple.dlowji.ffms_refactor.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployeeForm extends SignUpForm{
	@NotNull(message = "Please enter identity card number")
	@NotEmpty(message = "Please enter identity card number")
	@Length(min = 6, max = 10, message = "Please enter valid identity card " +
	"number")
	private String identityCard;

	private String description;

	@NotNull(message = "Please enter salary")
	private double salary;

	@NotNull(message = "Please choose field group")
	private Long fieldGroupId;
}
