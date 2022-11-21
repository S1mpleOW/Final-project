package s1mple.dlowji.ffms_refactor.security.userprincipal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.converters.SexConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.SexType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserPrincipal implements UserDetails {
	private Long id;

	private String fullName;

	@Convert(converter = SexConverter.class)
	private SexType sex;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dob;

	private String address;

	private String phone;

	private String email;

	private String username;

	private String password;
	private Collection<? extends SimpleGrantedAuthority> roles;

	public static UserPrincipal build(Account account) {
		List<SimpleGrantedAuthority> authorities =
		account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().getName())).collect(Collectors.toList());
		return UserPrincipal.builder()
		.id(account.getId())
		.address(account.getAddress())
		.dob(account.getDob())
		.fullName(account.getFullName())
		.sex(account.getSex())
		.email(account.getEmail())
		.username(account.getUsername())
		.password(account.getPassword())
		.phone(account.getPhone())
		.roles(authorities)
		.build();
	}

	@Override
	public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
