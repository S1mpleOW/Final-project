package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "import_receipt_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportReceiptDetail {
	@EmbeddedId
	private ImportReceiptDetailKey id;

	@JoinColumn(name = "IMPORT_RECEIPT_ID")
	@MapsId("importReceiptId")
	@ManyToOne
	private ImportReceipt importReceipt;

	@JoinColumn(name = "ITEM_ID")
	@MapsId("itemId")
	@ManyToOne
	private Item item;

	@Column(name = "DELIVERY_DATE")
	private LocalDateTime deliveryDate;

	@CreationTimestamp
	@Column(name = "ORDER_DATE")
	private ZonedDateTime orderDate;

	@Column(name = "QUANTITY")
	private int quantity;
}
