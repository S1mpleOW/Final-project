package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.EquipmentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.FieldType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EquipmentStatusConverter implements AttributeConverter<EquipmentStatus, String> {
	@Override
	public String convertToDatabaseColumn(EquipmentStatus equipmentStatus) {
		if(equipmentStatus == null) {
			return null;
		}
		return equipmentStatus.toString();
	}

	@Override
	public EquipmentStatus convertToEntityAttribute(String s) {
		if(s == null) {
			return null;
		}
		return Stream.of(EquipmentStatus.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
	}
}
