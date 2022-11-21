package s1mple.dlowji.ffms_refactor.entities.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.entities.FootballField;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Projection(name = "partialBooked", types = {BookedTicketDetail.class})
public interface BookedFieldProjection {

	@Value("#{target.footballField}")
	FootballField getFootballField();

	@Value("#{target.startTime}")
	LocalDateTime getStartTime();

	@Value("#{target.endTime}")
	LocalDateTime getEndTime();

	@Value("#{target.bookedTicket}")
	BookedTicket getBookedTicket();
}
