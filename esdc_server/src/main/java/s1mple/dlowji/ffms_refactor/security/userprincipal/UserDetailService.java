package s1mple.dlowji.ffms_refactor.security.userprincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.repositories.AccountRepository;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account =
		accountRepository.findAccountByUsername(username).orElseThrow(() ->
		new UsernameNotFoundException("User name not found in the db"));
		return UserPrincipal.build(account);
	}


}
