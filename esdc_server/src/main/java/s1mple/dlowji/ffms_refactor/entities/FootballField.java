package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;
import s1mple.dlowji.ffms_refactor.entities.converters.FieldTypeConverter;
import s1mple.dlowji.ffms_refactor.entities.converters.SexConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.FieldType;

import javax.persistence.*;

@Entity
@Table(name = "football_field")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FootballField extends AbstractEntity {
	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE")
	@Convert(converter = FieldTypeConverter.class)
	private FieldType type;

	@Column(name = "IMAGE")
	@Lob
	private String image;

	@Column(name = "PRICE" , columnDefinition = "DOUBLE DEFAULT 0")
	private double price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FIELD_GROUP_ID", referencedColumnName = "ID")
	private FieldGroup fieldGroup;
}
