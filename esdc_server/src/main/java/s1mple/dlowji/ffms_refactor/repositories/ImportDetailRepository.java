package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.ImportReceiptDetail;

@Repository
public interface ImportDetailRepository extends JpaRepository<ImportReceiptDetail, Long> {
}
