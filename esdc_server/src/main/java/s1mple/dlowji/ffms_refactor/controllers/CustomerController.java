package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.CustomerDTO;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.repositories.CustomerRepository;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")

public class CustomerController {
	@Autowired
	private ICustomerServiceImpl customerService;

	@GetMapping("/customers")
	@ResponseBody
	public ResponseEntity<?> getCustomers(Pageable pageable) {
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		List<String> authorities =
		userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		if (authorities.contains(RoleName.ADMIN.getName()) || authorities.contains(RoleName.EMPLOYEE.getName())) {
			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.OK.value());
			response.put("data", customerService.findAll(pageable));
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.badRequest().body(new ResponseMessage("You don't " +
		"have permission to access", HttpStatus.BAD_REQUEST.value()));
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long currentAccountId = userPrincipal.getId();
		Optional<Customer> customerOptional = customerService.findCustomerById(id);
		if (currentAccountId == 1 && customerOptional.isPresent()) {
			return ResponseEntity.ok(customerOptional.get());
		}
		Optional<Customer> customerCurrent =
		customerService.findCustomerByAccountId(currentAccountId);
		List<String> authorities =
		userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		if (authorities.contains(RoleName.ADMIN.getName()) || authorities.contains(RoleName.EMPLOYEE.getName())) {
			return ResponseEntity.ok(customerOptional.get());
		}
		if (!customerCurrent.isPresent()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Bad request"
			, HttpStatus.NO_CONTENT.value()));
		}
		Long currentCustomerId = customerCurrent.get().getId();
		if (currentCustomerId == id) {
			return ResponseEntity.ok(customerOptional.get());
		}
		return ResponseEntity.badRequest().body(new ResponseMessage("Bad request"
		, HttpStatus.NO_CONTENT.value()));
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id,
																					@RequestBody CustomerDTO customerDTO) {
		System.out.println(customerDTO);
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		List<String> authorities =
		userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		if (!authorities.contains(RoleName.ADMIN.getName()) && !authorities.contains(RoleName.EMPLOYEE.getName())) {
			return ResponseEntity.badRequest().body(new ResponseMessage("You don't " +
			"have permission to access"
			, HttpStatus.NO_CONTENT.value()));
		}
		Optional<Customer> customerOptional = customerService.findCustomerById(id);
		if (!customerOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Bad request"
			, HttpStatus.NO_CONTENT.value()));
		}
		Customer customer = customerOptional.get();
		Account account = customer.getAccount();
		if (customerDTO.getAddress() != null && !customerDTO.getAddress().isEmpty()) {
			account.setAddress(customerDTO.getAddress());
		}

		if (customerDTO.getPhone() != null && !customerDTO.getPhone().isEmpty()) {
			account.setPhone(customerDTO.getPhone());
		}

		if (customerDTO.getEmail() != null && !customerDTO.getEmail().isEmpty()) {
			account.setEmail(customerDTO.getEmail());
		}

		if (customerDTO.getFullName() != null && !customerDTO.getFullName().isEmpty()) {
			account.setFullName(customerDTO.getFullName());
		}

		if (customerDTO.getDob() != null) {
			account.setDob(customerDTO.getDob());
		}

		if (customerDTO.getSex() != null) {
			account.setSex(customerDTO.getSex());
		}

		if (customerDTO.getRewardPoint() != null) {
			customer.setRewardPoint(customerDTO.getRewardPoint());
		}
		customer.setAccount(account);
		customerService.save(customer);
		return ResponseEntity.ok(customerService.findCustomerById(id).get());
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		List<String> authorities =
		userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		if (!authorities.contains(RoleName.ADMIN.getName()) && !authorities.contains(RoleName.EMPLOYEE.getName())) {
			return ResponseEntity.badRequest().body(new ResponseMessage("You don't " +
			"have permission to access", HttpStatus.BAD_REQUEST.value()));
		}

		Optional<Customer> customer = customerService.findCustomerById(id);
		if (!customer.isPresent()) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Can't find" +
			" the customer", HttpStatus.BAD_REQUEST.value()));
		}
		customerService.deleteById(id);
		return ResponseEntity.ok().body(new ResponseMessage("Delete successfully"
		, HttpStatus.OK.value()));
	}
}
