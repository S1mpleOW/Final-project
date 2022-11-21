package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.entities.converters.PaymentConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedTicketDTO {

	private int totalPrice;

	private PaymentStatus paymentStatus;

	private String note;
}
