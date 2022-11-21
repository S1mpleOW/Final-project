package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "employee")
@Builder
public class Employee extends AbstractEntity{
	@Column(name = "IDENTITY_CARD")
	private String identityCard;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SALARY", columnDefinition = "DOUBLE DEFAULT 0")
	private double salary;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FIELD_GROUP_ID", referencedColumnName = "ID")
	private FieldGroup fieldGroup;

	@OneToOne(fetch =  FetchType.EAGER, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
	private Account account;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private ZonedDateTime updatedAt;

	@Column(name = "IS_DELETED", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean isDeleted;
}
