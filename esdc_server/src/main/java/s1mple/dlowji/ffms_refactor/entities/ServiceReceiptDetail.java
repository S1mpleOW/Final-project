package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "service_receipt_detail")
public class ServiceReceiptDetail {
    @EmbeddedId
    private ServiceReceiptDetailKey id;

    @JoinColumn(name = "ITEM_ID")
    @MapsId("itemId")
    @ManyToOne
    private Item item;

    @JoinColumn(name = "SERVICE_RECEIPT_ID")
    @MapsId("serviceReceiptId")
    @ManyToOne
    private ServiceReceipt serviceReceipt;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ORDER_DATE")
    @CreationTimestamp
    private ZonedDateTime orderDate;
}
