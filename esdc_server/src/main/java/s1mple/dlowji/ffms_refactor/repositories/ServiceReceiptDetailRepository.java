package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.ServiceReceiptDetail;

@RepositoryRestResource(exported = false)
public interface ServiceReceiptDetailRepository extends CrudRepository<ServiceReceiptDetail, Long> {

}
