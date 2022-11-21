package s1mple.dlowji.ffms_refactor.entities.converters;

import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentConverter implements AttributeConverter<PaymentStatus, String> {
	@Override
	public String convertToDatabaseColumn(PaymentStatus paymentStatus) {
		if(paymentStatus == null) {
			return null;
		}
		return paymentStatus.toString();
	}

	@Override
	public PaymentStatus convertToEntityAttribute(String s) {
		if(s == null) {
			return null;
		}
		return Stream.of(PaymentStatus.values())
		.filter(c -> c.toString().equalsIgnoreCase(s))
		.findFirst().get();
	}
}
