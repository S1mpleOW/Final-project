package s1mple.dlowji.ffms_refactor.services;


import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;

import java.util.Optional;

public interface IRoleService {
	Optional<Role> findByName(String name);

	Optional<Role> findByName(RoleName name);

	Role save(Role role);
}
