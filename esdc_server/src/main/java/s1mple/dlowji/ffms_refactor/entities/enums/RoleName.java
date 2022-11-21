package s1mple.dlowji.ffms_refactor.entities.enums;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
public enum RoleName {
	ADMIN ("ADMIN"),
	EMPLOYEE ("EMPLOYEE"),
	USER ("USER");
	private String name;

	private RoleName(){}

	private RoleName(String name) {
		this.name = name;
	}

}
