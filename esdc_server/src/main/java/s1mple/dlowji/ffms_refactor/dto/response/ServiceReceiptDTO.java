package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceReceiptDTO {
	private PaymentStatus paymentStatus;

	private int totalPrice;

	private String note;
}
