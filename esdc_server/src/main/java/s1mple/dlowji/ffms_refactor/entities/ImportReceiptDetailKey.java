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
public class ImportReceiptDetailKey implements Serializable {
    @Column(name = "IMPORT_RECEIPT_ID")
    private Long importReceiptId;

    @Column(name = "ITEM_ID")
    private Long itemId;
}
