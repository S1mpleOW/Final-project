package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.FieldGroup;

import java.util.Optional;

@RepositoryRestResource(path = "groups", collectionResourceRel = "groups")
@PreAuthorize("hasAuthority('ADMIN')")
public interface FieldGroupRepository extends CrudRepository<FieldGroup, Long> {
	FieldGroup save(FieldGroup fieldGroup);
}
