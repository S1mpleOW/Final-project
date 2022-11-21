package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketDetailRepository;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketRepository;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IBookedTicketServiceImpl implements IBookedTicketService {
	@Autowired
	private BookedTicketRepository bookedTicketRepository;

	@Autowired
	private BookedTicketDetailRepository bookedTicketDetailRepository;

	@Override
	public BookedTicket save(BookedTicket bookedTicket) {
		return bookedTicketRepository.save(bookedTicket);
	}

	@Override
	public BookedTicketDetail save(BookedTicketDetail bookedTicketDetail) {
		return bookedTicketDetailRepository.save(bookedTicketDetail);
	}

	@Override
	public List<BookedTicketDetail> getPlayedBookedTicketDetailByDate(LocalDateTime dateTime) {
		List<BookedTicketDetail> bookedTicketDetailList = bookedTicketDetailRepository.findAll();

		for (BookedTicketDetail btd:bookedTicketDetailList) {
			LocalDate date = btd.getStartTime().toLocalDate();
			LocalDate checkDate = dateTime.toLocalDate();

			if (!date.isEqual(checkDate)) {
				bookedTicketDetailList.remove(btd);
				continue;
			}
			if (!btd.getEndTime().isBefore(dateTime)) {
				bookedTicketDetailList.remove(btd);
			}
		}

		return bookedTicketDetailList;
	}

	@Override
	public List<BookedTicketDetail> getPlayingBookedTicketDetailByDate(LocalDateTime dateTime) {
		List<BookedTicketDetail> bookedTicketDetailList = new ArrayList<>();

		for (BookedTicketDetail btd:bookedTicketDetailRepository.findAll()) {
			LocalDate date = btd.getStartTime().toLocalDate();
			LocalDate checkDate = dateTime.toLocalDate();

			if (!date.isEqual(checkDate)) {
				continue;
			}

			if (btd.getStartTime().isBefore(dateTime) && btd.getEndTime().isAfter(dateTime)) {
				bookedTicketDetailList.add(btd);
			}
		}

		return bookedTicketDetailList;
	}

	@Override
	public List<BookedTicketDetail> getWillPlayBookedTicketDetailByDate(LocalDateTime dateTime) {
		List<BookedTicketDetail> bookedTicketDetailList = new ArrayList<>();

		for (BookedTicketDetail btd:bookedTicketDetailRepository.findAll()) {
			LocalDate date = btd.getStartTime().toLocalDate();
			LocalDate checkDate = dateTime.toLocalDate();

			if (!date.isEqual(checkDate)) {
				continue;
			}

			if (btd.getStartTime().isAfter(dateTime)) {
				bookedTicketDetailList.add(btd);
			}
		}

		return bookedTicketDetailList;
	}

	@Override
	public List<BookedTicketDetail> getBookedTicketDetailByWeek(LocalDateTime firstDay, LocalDateTime lastDay, Long field_id) {
		List<BookedTicketDetail> bookedTicketDetailList = new ArrayList<>();

		for (BookedTicketDetail btd : bookedTicketDetailRepository.findAll()) {
			if (btd.getFootballField().getId() == field_id && btd.getStartTime().isAfter(firstDay) && btd.getStartTime().isBefore(lastDay) && !btd.isCanceled()) {
				bookedTicketDetailList.add(btd);
			}
		}
		return bookedTicketDetailList;
	}

	@Override
	public List<BookedTicket> getBookedTicketByQuarter(int year, int quarter) {
		List<BookedTicket> bookedTicketList = new ArrayList<>();

		for (BookedTicket receipt: bookedTicketRepository.findAll()) {
			if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().get(IsoFields.QUARTER_OF_YEAR) == quarter) {
				bookedTicketList.add(receipt);
			}
		}

		return bookedTicketList;
	}

	@Override
	public int getBookedPriceByMonth(int month, int year) {
		List<BookedTicket> bookedTicketList = new ArrayList<>();

		for (BookedTicket receipt: bookedTicketRepository.findAll()) {
			if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month) {
				bookedTicketList.add(receipt);
			}
		}

		int totalPrice = 0;

		for (BookedTicket receipt:bookedTicketList) {
			boolean isNotCancel =
			receipt.getBookedTicketDetails()
			.stream().filter(bookedTicketDetail -> bookedTicketDetail.isCanceled())
			.collect(Collectors.toList()).isEmpty();
			if(isNotCancel) {
				totalPrice += receipt.getTotalPrice();
			}
		}

		return totalPrice;
	}

	@Override
	public int getBookedPriceByDay(int day, int month, int year) {
		List<BookedTicket> bookedTicketList = new ArrayList<>();

		for (BookedTicket receipt: bookedTicketRepository.findAll()) {
			if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month && receipt.getCreatedAt().getDayOfMonth() == day) {
				bookedTicketList.add(receipt);
			}
		}

		int totalPrice = 0;

		for (BookedTicket receipt:bookedTicketList) {
			totalPrice += receipt.getTotalPrice();
		}

		return totalPrice;
	}
}
