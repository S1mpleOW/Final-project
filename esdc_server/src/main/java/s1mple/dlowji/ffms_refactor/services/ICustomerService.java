package s1mple.dlowji.ffms_refactor.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import s1mple.dlowji.ffms_refactor.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
	Page<Customer> findAll(Pageable pageable);

	Customer save(Customer customer);

	boolean existsById(Long id);

	Optional<Customer> findCustomerById(Long id);

	Customer deleteById(Long id);

	Optional<Customer> findCustomerByAccountId(Long id);

	boolean existsByPhoneNumber(String phone);

	Optional<Customer> findCustomerByPhone(String phone);

	Customer findCustomerByUsername(String username);

}
