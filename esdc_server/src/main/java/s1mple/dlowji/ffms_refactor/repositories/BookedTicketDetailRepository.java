package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.entities.projections.BookedFieldProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = BookedFieldProjection.class)
public interface BookedTicketDetailRepository extends JpaRepository<BookedTicketDetail, Long> {
	List<BookedTicketDetail> findByStartTimeGreaterThanAndStartTimeLessThanOrEndTimeGreaterThanAndEndTimeLessThan(LocalDateTime startTime,
																																																								LocalDateTime endTime,
																																																								LocalDateTime startTimeEnd,
																																																								LocalDateTime endTimeEnd);
	Optional<BookedTicketDetail> findByBookedTicket_Id(Long id);
}
