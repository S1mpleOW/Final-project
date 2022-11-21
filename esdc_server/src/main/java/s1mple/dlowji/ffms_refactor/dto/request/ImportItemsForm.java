package s1mple.dlowji.ffms_refactor.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.enums.EquipmentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.ItemCategory;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ImportItemsForm {
    @NotNull(message = "Supplier id must not null")
    private Long supplier_id;

    @NotNull(message = "Image must not null")
    private String image;

    @NotNull(message = "Import price must not null")
    @Min(value = 0, message = "Import price must greater or equal 0")
    private int import_price;

    @NotNull(message = "Item category must not null")
    private ItemCategory item_category;

    @NotNull(message = "Item's name must not null")
    private String name;

    private String note;

    @NotNull(message = "Quantity must not null")
    @Min(value = 0, message = "Quantity must greater or equal 0")
    private int quantity;

    @NotNull(message = "Item unit must not null")
    private String unit;

    // format date time yyyy-MM-dd'T'HH:mm:ss.SSSXXX
    @NotNull(message = "Please enter delivery date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime delivery_date;
}
