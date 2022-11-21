package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);

	Optional<Role> findRoleByName(String name);
}
