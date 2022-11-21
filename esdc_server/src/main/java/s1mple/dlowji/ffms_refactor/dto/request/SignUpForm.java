package s1mple.dlowji.ffms_refactor.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SignUpForm {
	@NotNull(message = "Please enter full name")
	@NotEmpty(message = "Please enter full name")
	private String fullName;

	@NotNull(message = "Please enter sex type")
	@Enumerated(EnumType.STRING)
	private SexType sex;

	@NotNull(message = "Please enter birthday")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dob;

	@NotNull(message = "Please enter address")
	@NotEmpty(message = "Please enter address")
	private String address;

	@NotNull(message = "Please enter phone number")
	@NotEmpty(message = "Please enter phone number")
	@Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})", message = "Phone number" +
	" is not valid")
	private String phone;

	@NotNull(message = "Please enter email")
	@NotEmpty(message = "Please enter email")
	@Email(message = "Please enter valid email", regexp = "^[\\w!#$" +
	"%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@NotNull(message = "Please enter username")
	@NotEmpty(message = "Please enter username")
	private String username;

	@NotNull(message = "Please enter password")
	@NotEmpty(message = "Please enter password")
	@Length(min = 6, max = 32)
	private String password;

	private String description;

	@Lob
	private String avatar;

	private Set<String> roles;
}
