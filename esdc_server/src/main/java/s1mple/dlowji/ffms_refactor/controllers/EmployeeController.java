package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.AddEmployeeForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.repositories.FieldGroupRepository;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.IAccountService;
import s1mple.dlowji.ffms_refactor.services.IEmployeeServices;
import s1mple.dlowji.ffms_refactor.services.impl.IRoleServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	private IAccountService iAccountService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private IRoleServiceImpl iRoleService;

	@Autowired
	private FieldGroupRepository fieldGroupRepository;

	@Autowired
	private IEmployeeServices iEmployeeServices;

	@PostMapping("/employees/register")
	public ResponseEntity<?> register(@Valid @RequestBody AddEmployeeForm signUpForm) {
		try {
			if (iAccountService.existsByUsername(signUpForm.getUsername())) {
				return new ResponseEntity<>(new ResponseMessage("The username is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			if (iAccountService.existsByEmail(signUpForm.getEmail())) {
				return new ResponseEntity<>(new ResponseMessage("The email is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			if (iAccountService.existsByPhone(signUpForm.getPhone())) {
				return new ResponseEntity<>(new ResponseMessage("The phone number is " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
			if(employeeRepository.existsEmployeeByIdentityCard(signUpForm.getIdentityCard())) {
				return new ResponseEntity<>(new ResponseMessage("The identity card is" +
				" existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}

			if(String.valueOf(signUpForm.getSalary()).length() <= 0) {
				return new ResponseEntity<>(new ResponseMessage("The salary is must " +
				"not equal or less than zero",	HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
			}
			Set<Role> roles = new HashSet<>();
			Optional<Role> employeeRole = iRoleService.findByName(RoleName.EMPLOYEE);
			Optional<Role> userRole = iRoleService.findByName(RoleName.USER);
			roles.add(employeeRole.get());
			roles.add(userRole.get());
			Account account =
			Account.builder()
			.email(signUpForm.getEmail())
			.phone(signUpForm.getPhone())
			.sex(signUpForm.getSex())
			.dob(signUpForm.getDob())
			.address(signUpForm.getAddress())
			.fullName(signUpForm.getFullName())
			.username(signUpForm.getUsername())
			.password(signUpForm.getPassword())
			.build();

			account.setRoles(roles);

			Optional<FieldGroup> fieldGroupOptional =
			fieldGroupRepository.findById(signUpForm.getFieldGroupId());
			FieldGroup fieldGroup = null;

			if(fieldGroupOptional.isPresent() && !fieldGroupOptional.isEmpty()) {
				fieldGroup = fieldGroupOptional.get();
			}
			else {
				return new ResponseEntity<>(new ResponseMessage("The field group is " +
				"not existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}

			Employee employee = Employee.builder()
			.salary(signUpForm.getSalary())
			.account(account)
			.fieldGroup(fieldGroup)
			.description(signUpForm.getDescription())
			.identityCard(signUpForm.getIdentityCard())
			.build();
			iAccountService.save(account);
			employeeRepository.save(employee);
			return new ResponseEntity<>(new ResponseMessage("Create success",
			HttpStatus.OK.value()),
			HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponseMessage(e.getMessage(),
			HttpStatus.INTERNAL_SERVER_ERROR.value()),
			HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		List<String> authorities =
		userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		if (!authorities.contains(RoleName.ADMIN.getName())) {
			return ResponseEntity.badRequest().body(new ResponseMessage("You don't " +
			"have permission to access", HttpStatus.BAD_REQUEST.value()));
		}

		Employee employee = iEmployeeServices.findEmployeeById(id);
		if (employee == null) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Can't find" +
			" the employee", HttpStatus.BAD_REQUEST.value()));
		}
		iEmployeeServices.deleteById(id);
		return ResponseEntity.ok().body(new ResponseMessage("Delete successfully"
		, HttpStatus.OK.value()));
	}
}
