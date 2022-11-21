package s1mple.dlowji.ffms_refactor.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
public class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@CreationTimestamp
	@Column(name = "CREATED_AT", updatable=false)
	private ZonedDateTime createdAt;

	public Long getResourceId() {
		return id;
	}
}
