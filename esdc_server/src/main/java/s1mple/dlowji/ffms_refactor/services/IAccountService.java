package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
	Optional<Account> findAccountByEmail(String email);

	Optional<Account> findAccountByUsername(String username);

	Optional<Account> findAccountByPhone(String phone);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByPhone(String phone);

	Account save(Account account);

	List<Account> findAll();
}
