package s1mple.dlowji.ffms_refactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.annotation.Order;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;
import s1mple.dlowji.ffms_refactor.services.IAccountService;
import s1mple.dlowji.ffms_refactor.services.IRoleService;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EntityScan(basePackageClasses = { FfmsRefactorApplication.class,
Jsr310Converters.class })

public class FfmsRefactorApplication{

	@Autowired
	private IRoleService iRoleService;

	@Autowired
	private IAccountService iAccountService;
	public static void main(String[] args) {
		SpringApplication.run(FfmsRefactorApplication.class, args);
	}

	@Component
	@Order(1)
	class setupRole implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			if(iRoleService.findByName(RoleName.ADMIN).isPresent()) {
				return;
			}
			Role adminRole = Role.builder().id(1L).name(RoleName.ADMIN).build();
			Role employeeRole = Role.builder().id(2L).name(RoleName.EMPLOYEE).build();
			Role userRole = Role.builder().id(3L).name(RoleName.USER).build();
			iRoleService.save(adminRole);
			iRoleService.save(employeeRole);
			iRoleService.save(userRole);
		}
	}

	@Component
	@Order(2)
	class setupAdminAccount implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			if(iAccountService.existsByUsername("s1mple")) {
				return;
			}

			Set<Role> roles = new HashSet<>();
			Optional<Role> adminRole = iRoleService.findByName(RoleName.ADMIN);
			Optional<Role> userRole = iRoleService.findByName(RoleName.USER);
			Optional<Role> employeeRole = iRoleService.findByName(RoleName.EMPLOYEE);
			roles.add(adminRole.get());
			roles.add(userRole.get());
			roles.add(employeeRole.get());
			Account account =
			Account.builder()
			.email("dungdqch@gmail.com")
			.phone("0325252522")
			.sex(SexType.MALE)
			.dob(LocalDateTime.of(2002, Month.SEPTEMBER, 4, 0, 0))
			.address("170/9 Nguyen Xi")
			.fullName("s1mple")
			.username("s1mple")
			.password("123456")
			.build();
			account.setRoles(roles);
			iAccountService.save(account);
		}
	}
}
