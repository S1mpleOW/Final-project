package s1mple.dlowji.ffms_refactor.entities.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Customer;

import java.time.LocalDateTime;

@Projection(name = "partialCustomer", types = {Customer.class})
public interface PartialCustomerProjection {
	@Value("#{target.account.fullName}")
	String getFullName();

	@Value("#{target.account.sex}")
	String getSex();

	@Value("#{target.account.dob}")
	LocalDateTime getDob();

	@Value("#{target.account.address}")
	String getAddress();

	@Value("#{target.account.phone}")
	String getPhone();

	@Value("#{target.account.email}")
	String getEmail();

	@Value("#{target.account.username}")
	String getUsername();

	@Value("#{target.rewardPoint}")
	Long getRewardPoint();
}
