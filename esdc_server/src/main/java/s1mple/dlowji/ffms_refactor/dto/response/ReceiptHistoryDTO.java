package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptHistoryDTO {
	private int totalPrice;

	private PaymentStatus paymentStatus;

	private String note;
}
