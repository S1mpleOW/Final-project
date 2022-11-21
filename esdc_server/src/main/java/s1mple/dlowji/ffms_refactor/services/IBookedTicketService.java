package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookedTicketService {
	BookedTicket save(BookedTicket bookedTicket);

	BookedTicketDetail save(BookedTicketDetail bookedTicketDetail);

	List<BookedTicketDetail> getPlayedBookedTicketDetailByDate(LocalDateTime dateTime);

	List<BookedTicketDetail> getPlayingBookedTicketDetailByDate(LocalDateTime dateTime);

	List<BookedTicketDetail> getWillPlayBookedTicketDetailByDate(LocalDateTime dateTime);

	List<BookedTicketDetail> getBookedTicketDetailByWeek(LocalDateTime firstDay, LocalDateTime lastDay, Long field_id);

	List<BookedTicket> getBookedTicketByQuarter(int year, int quarter);

	int getBookedPriceByMonth(int month, int year);

	 int getBookedPriceByDay(int day, int month, int year);
}
