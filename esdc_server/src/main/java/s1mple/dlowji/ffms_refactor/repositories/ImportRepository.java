package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;

import java.util.List;

@Repository
public interface ImportRepository extends JpaRepository<ImportReceipt, Long> {
    List<ImportReceipt> findAll();

    List<ImportReceipt> findImportReceiptsByEmployee_Id(Long employeeId);
}
