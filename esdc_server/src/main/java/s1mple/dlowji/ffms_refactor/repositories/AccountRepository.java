package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import s1mple.dlowji.ffms_refactor.entities.Account;
import s1mple.dlowji.ffms_refactor.entities.projections.PartialAccountProjection;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = PartialAccountProjection.class, exported = false)
public interface AccountRepository extends CrudRepository<Account, Long> {
	Optional<Account> findAccountByEmail(String email);
	Optional<Account> findAccountByUsername(String username);
	Optional<Account> findAccountByPhone(String phone);

	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByPhone(String phone);

}
