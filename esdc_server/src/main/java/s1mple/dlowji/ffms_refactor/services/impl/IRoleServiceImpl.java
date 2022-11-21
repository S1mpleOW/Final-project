package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Role;
import s1mple.dlowji.ffms_refactor.entities.enums.RoleName;
import s1mple.dlowji.ffms_refactor.repositories.RoleRepository;
import s1mple.dlowji.ffms_refactor.services.IRoleService;

import java.util.Optional;

@Service
public class IRoleServiceImpl implements IRoleService {
	@Autowired
 	private RoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(String name) {
		return roleRepository.findRoleByName(name);
	}

	@Override
	public Optional<Role> findByName(RoleName name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}
}
