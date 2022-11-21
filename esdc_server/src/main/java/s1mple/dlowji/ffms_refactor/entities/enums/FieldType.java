package s1mple.dlowji.ffms_refactor.entities.enums;

import lombok.Getter;

@Getter
public enum FieldType {
	FIELD_5,
	FIELD_7,
	FIELD_11;

	private String type;
	private FieldType(){}

	private FieldType(String type) {
		this.type = type;
	}
}
