package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import s1mple.dlowji.ffms_refactor.entities.Services;

@RepositoryRestResource(path = "services", collectionResourceRel = "services")
public interface ServicesRepository extends JpaRepository<Services, Long> {
}
