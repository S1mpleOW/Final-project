package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceReceiptDetailKey implements Serializable {
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "SERVICE_RECEIPT_ID")
    private Long serviceReceiptId;
}
