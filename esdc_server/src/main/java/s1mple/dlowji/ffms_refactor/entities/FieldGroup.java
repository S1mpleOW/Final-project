package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "field_group")
public class FieldGroup extends AbstractEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "OPEN_TIME")
	private LocalTime openTime;

	@Column(name = "CLOSE_TIME")
	private LocalTime closeTime;

	@Column(name = "FIELD_FEE_WEIGHT")
	private double fieldFeeWeight;

	@OneToMany(mappedBy = "fieldGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FootballField> footballFields;

	@OneToMany(mappedBy = "fieldGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> users;
}
