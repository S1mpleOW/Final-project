package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.converters.EquipmentStatusConverter;
import s1mple.dlowji.ffms_refactor.entities.converters.ItemCategoryConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.EquipmentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.ItemCategory;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "item")
public class Item extends AbstractEntity {
	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	@Convert(converter = EquipmentStatusConverter.class)
	private EquipmentStatus status;

	@Column(name = "IMPORT_PRICE")
	private int importPrice;

	@Column(name = "UNIT")
	private String unit;

	@Column(name = "IMAGE")
	@Lob
	private String image;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "CATEGORY")
	@Convert(converter = ItemCategoryConverter.class)
	private ItemCategory itemCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
	private Supplier supplier;

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<ServiceReceiptDetail> serviceReceiptDetails;
}
