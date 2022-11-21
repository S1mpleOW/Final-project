package s1mple.dlowji.ffms_refactor.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.converters.SexConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CustomerDTO {
	private String fullName;

	@Convert(converter = SexConverter.class)
	private SexType sex;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime dob;

	private String address;

	private String phone;

	private String email;

	private Long rewardPoint;
}
