package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Item;
import s1mple.dlowji.ffms_refactor.entities.ServiceReceipt;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	@RestResource(path="category", rel = "category")
	@Query("select i from Item i where lower(i.itemCategory) like lower(concat" +
	"('%', ?1, '%'))")
	Page<Item> searchItemsByItemCategoryEqualsIgnoreCase(@Param("category") String itemCategory, Pageable pageable);
	@Override
	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	Item save(Item entity);

	@Override
	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	void deleteById(Long aLong);

	boolean existsByNameIgnoreCase(String name);

	Item findByNameIgnoreCase(String name);

	@RestResource(path = "supplier", rel = "items")
	Page<Item> searchItemsBySupplier_Id(@Param("id") Long id,
																			Pageable pageable);

	Item findItemById(Long id);
}
