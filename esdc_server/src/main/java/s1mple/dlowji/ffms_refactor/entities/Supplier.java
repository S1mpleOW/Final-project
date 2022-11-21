package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="supplier")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Supplier extends AbstractEntity {
	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "NOTE")
	private String note;
}
