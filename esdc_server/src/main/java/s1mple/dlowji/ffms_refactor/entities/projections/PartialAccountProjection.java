package s1mple.dlowji.ffms_refactor.entities.projections;

import org.springframework.data.rest.core.config.Projection;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Customer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Projection(name = "partialAccount", types = {Account.class})
public interface PartialAccountProjection {
	String getFullName();

	String getSex();

	LocalDateTime getDob();

	String getAddress();

	String getPhone();

	String getEmail();

	String getUsername();
}
