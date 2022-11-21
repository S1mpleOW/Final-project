package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.FieldType;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class FieldTypeConverter  implements AttributeConverter<FieldType, String> {
	@Override
	public String convertToDatabaseColumn(FieldType fieldType) {
		System.out.println(fieldType);
		if(fieldType == null) {
			return null;
		}
		return fieldType.toString();
	}

	@Override
	public FieldType convertToEntityAttribute(String s) {
		if(s == null) {
			return null;
		}
		return Stream.of(FieldType.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
	}
}
