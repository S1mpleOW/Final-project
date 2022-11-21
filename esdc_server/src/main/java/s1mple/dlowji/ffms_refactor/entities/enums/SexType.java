package s1mple.dlowji.ffms_refactor.entities.enums;

import lombok.Getter;

@Getter
public enum SexType {
	MALE,
	FEMALE,
	OTHER;
	private String type;

	private SexType() {}

	private SexType(String type) {
		this.type = type;
	}

	public String getSex() {
		return type;
	}
}
