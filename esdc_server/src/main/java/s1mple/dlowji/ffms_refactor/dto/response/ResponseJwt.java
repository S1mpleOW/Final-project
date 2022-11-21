package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseJwt {
	private String token;
	private String name;
	private String type = "Bearer";
	private List<String> roles;
	private int status;
}
