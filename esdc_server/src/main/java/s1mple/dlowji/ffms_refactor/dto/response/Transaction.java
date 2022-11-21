package s1mple.dlowji.ffms_refactor.dto.response;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.entities.ServiceReceipt;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	private ZonedDateTime createdAt;

	private String type;

	private ReceiptHistoryDTO bookedTicket;

	private ReceiptHistoryDTO serviceReceipt;

	private ReceiptHistoryDTO importReceipt;

}
