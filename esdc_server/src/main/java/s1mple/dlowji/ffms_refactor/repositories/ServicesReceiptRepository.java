package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.ServiceReceipt;

import java.util.List;


@Repository
public interface ServicesReceiptRepository extends CrudRepository<ServiceReceipt,
Long> {
    List<ServiceReceipt> findAll();

    List<ServiceReceipt> findServiceReceiptsByUser_Id(Long employeeId);

    List<ServiceReceipt> findServiceReceiptsByEmployee_Id(Long employeeId);
}
