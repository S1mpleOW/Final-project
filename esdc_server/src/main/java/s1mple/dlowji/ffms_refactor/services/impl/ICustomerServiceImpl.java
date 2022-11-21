package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.repositories.CustomerRepository;
import s1mple.dlowji.ffms_refactor.services.ICustomerService;

import java.util.Optional;

@Service
public class ICustomerServiceImpl implements ICustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Value("${spring.data.rest.default-page-size}")
	private int pageSize;
	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAllByDeletedIsFalse(pageable);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	@Override
	public Optional<Customer> findCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public Customer deleteById(Long id) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if(optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
			Customer customer = optionalCustomer.get();
			customer.setDeleted(true);
			customerRepository.save(customer);
		}
		return optionalCustomer.get();
	}

	@Override
	public Optional<Customer> findCustomerByAccountId(Long id) {
		return customerRepository.findCustomerByAccount_Id(id);
	}

	@Override
	public boolean existsByPhoneNumber(String phone) {
		return customerRepository.existsCustomerByAccount_Phone(phone);
	}

	@Override
	public Optional<Customer> findCustomerByPhone(String phone) {

		return customerRepository.findCustomerByAccount_Phone(phone);
	}

	@Override
	public Customer findCustomerByUsername(String username) {
		Optional<Customer> optionalCustomer =
		customerRepository.findCustomerByAccount_Username(username);
		if(optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
			Customer customer = optionalCustomer.get();
			return customer;
		}
		return null;
	}
}
