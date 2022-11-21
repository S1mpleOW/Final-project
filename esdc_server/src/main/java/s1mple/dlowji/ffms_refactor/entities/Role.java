package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role {
	@Id
	@Column(nullable = false, updatable = false, name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NaturalId
	@Column(name = "NAME")
	@Enumerated(EnumType.STRING)
	private RoleName name;

}
