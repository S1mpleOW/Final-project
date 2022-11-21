package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {
	@Column(name = "REWARD_POINT", columnDefinition = "BIGINT DEFAULT 0")
	private Long rewardPoint;

	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
	@RestResource(exported = false)
	private Account account;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private ZonedDateTime updatedAt;

	@Column(name = "IS_DELETED", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean isDeleted;
}
