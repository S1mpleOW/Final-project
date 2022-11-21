package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<SexType, String> {
	@Override
	public String convertToDatabaseColumn(SexType sexType) {
		if(sexType == null) {
			return null;
		}
		return sexType.toString();
	}

	@Override
	public SexType convertToEntityAttribute(String s) {
		if (s == null) {
			return null;
		}
		SexType sex = Stream.of(SexType.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
		return sex;
	}
}
