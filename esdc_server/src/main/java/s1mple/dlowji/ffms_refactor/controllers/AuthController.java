package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.SignInForm;
import s1mple.dlowji.ffms_refactor.dto.request.SignUpForm;
import s1mple.dlowji.ffms_refactor.dto.request.TokenDTO;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseJwt;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.helper.JwtHelper;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.IAccountService;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;
import s1mple.dlowji.ffms_refactor.services.impl.IRoleServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private IAccountService iAccountService;

	@Autowired
	private IRoleServiceImpl iRoleService;

	@Autowired
	private ICustomerServiceImpl iCustomerService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
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
			Set<Role> roles = new HashSet<>();
			Optional<Role> userRole = iRoleService.findByName(RoleName.USER);
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
			Customer customer = Customer.builder().rewardPoint(0L).account(account).build();
			iAccountService.save(account);
			iCustomerService.save(customer);
			return new ResponseEntity<>(new ResponseMessage("Create success",
			HttpStatus.OK.value()),
			HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponseMessage(e.getMessage(),
			HttpStatus.INTERNAL_SERVER_ERROR.value()),
			HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(),
		signInForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		List<String> roles =
		userPrincipal.getRoles().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		if(roles.contains(RoleName.EMPLOYEE.getName()) && !roles.contains(RoleName.ADMIN.getName()) ) {
			Employee employee =
			employeeRepository.findEmployeeByAccount_Username(userPrincipal.getUsername());
			if(employee.isDeleted()) {
				Map<String, Object> response = new HashMap<>();
				response.put("status", HttpStatus.UNAUTHORIZED.value());
				response.put("message", "Account has been deleted. Please contact " +
				"with " +
				"administrator to resolve problem");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
		}

		if(roles.contains(RoleName.USER.getName()) && !roles.contains(RoleName.ADMIN.getName())) {
			Customer customer =
			iCustomerService.findCustomerByUsername(userPrincipal.getUsername());
			if(customer.isDeleted()) {
				Map<String, Object> response = new HashMap<>();
				response.put("status", HttpStatus.UNAUTHORIZED.value());
				response.put("message", "Account has been deleted. Please contact with " +
				"administrator to resolve problem");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}
		}

		String token = jwtHelper.createToken(authentication);
		return ResponseEntity.ok(ResponseJwt.builder().token(token).name(userPrincipal.getFullName()).roles(roles).type("Bearer").status(HttpStatus.OK.value()).build());
	}

	@GetMapping("/user-profile")
	public ResponseEntity<?> profile() {
		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Optional<Account> account =
		iAccountService.findAccountByUsername(userPrincipal.getUsername());
		Account accountResponse = null;
		if(account.isPresent() && !account.isEmpty()) {
			accountResponse = account.get();
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("account", accountResponse);
		return ResponseEntity.ok(response);
	}
}
