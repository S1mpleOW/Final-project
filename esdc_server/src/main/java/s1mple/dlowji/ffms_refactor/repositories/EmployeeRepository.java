package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	@PreAuthorize("hasAuthority('ADMIN')")
	boolean existsEmployeeByIdentityCard(String identityCard);

	@PreAuthorize("hasAuthority('ADMIN')")
	Optional<Employee> findEmployeeByAccount_Id(Long aLong);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	<S extends Employee> S save(S entity);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteById(Long aLong);

	@RestResource(path="groups", rel = "employees")
	@PreAuthorize("hasAuthority('ADMIN')")
	Page<Employee> findEmployeesByFieldGroup_Name (String fieldGroup_name, Pageable pageable);

	@PreAuthorize("hasAuthority('ADMIN')")
	@Override
	@Query("select i from Employee i where i.isDeleted = false")
	Page<Employee> findAll(Pageable pageable);

	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	Employee findEmployeeByAccount_Username(String username);
}
