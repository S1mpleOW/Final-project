package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.entities.FootballField;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IFootballFieldService {
	boolean existsById(Long id);

	boolean check_available_space(LocalDateTime startTime, LocalDateTime endTime);

	Optional<FootballField> findById(Long id);

	List<FootballField> getFieldByBookedTicketDetails(List<BookedTicketDetail> bookedTicketDetailList);

}
