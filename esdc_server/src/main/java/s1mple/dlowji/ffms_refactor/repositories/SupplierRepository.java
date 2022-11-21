package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Supplier;

@RepositoryRestResource
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	@Override
	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	<S extends Supplier> S save(S entity);

	@Override
	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	void deleteById(Long aLong);
}
