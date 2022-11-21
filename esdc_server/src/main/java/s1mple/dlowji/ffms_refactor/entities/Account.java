package s1mple.dlowji.ffms_refactor.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import s1mple.dlowji.ffms_refactor.entities.converters.SexConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "account")
@Builder
public class Account extends AbstractEntity {

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "SEX")
	@Convert(converter = SexConverter.class)
	private SexType sex;

	@Column(name = "DOB")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime dob;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private ZonedDateTime updatedAt;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "account_role", joinColumns = @JoinColumn(name =
	"ACCOUNT_ID",
	referencedColumnName = "ID"),
	inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName =
	"ID"))
	private Set<Role> roles = new HashSet<>();

}
