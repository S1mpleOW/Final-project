package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.ItemCategory;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemCategoryConverter implements AttributeConverter<ItemCategory
, String> {
	@Override
	public String convertToDatabaseColumn(ItemCategory itemCategory) {
		if(itemCategory == null) {
			return null;
		}
		return itemCategory.toString();
	}

	@Override
	public ItemCategory convertToEntityAttribute(String s) {
		if(s == null) {
			return null;
		}
		return Stream.of(ItemCategory.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
	}
}
