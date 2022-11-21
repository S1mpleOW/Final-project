package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.repositories.AccountRepository;
import s1mple.dlowji.ffms_refactor.services.IAccountService;

import java.util.List;
import java.util.Optional;

@Service
public class IAccountServiceImpl implements IAccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<Account> findAccountByEmail(String email) {
		return accountRepository.findAccountByEmail(email);
	}

	@Override
	public Optional<Account> findAccountByUsername(String username) {
		return accountRepository.findAccountByUsername(username);
	}

	@Override
	public Optional<Account> findAccountByPhone(String phone) {
		return accountRepository.findAccountByPhone(phone);
	}

	@Override
	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {
		return accountRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return accountRepository.existsByPhone(phone);
	}

	@Override
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}

	@Override
	public List<Account> findAll() {
		return (List<Account>) accountRepository.findAll();
	}
}
