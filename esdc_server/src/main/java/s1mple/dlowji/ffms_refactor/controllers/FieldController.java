package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.BookedTicketForm;
import s1mple.dlowji.ffms_refactor.dto.request.FieldOrderForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketDetailRepository;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;
import s1mple.dlowji.ffms_refactor.services.impl.IFootballFieldServiceImpl;

import javax.validation.Valid;
import java.sql.Date;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FieldController {
	@Autowired
	private IFootballFieldServiceImpl footballFieldService;

	@Autowired
	private ICustomerServiceImpl customerService;

	@Autowired
	private IBookedTicketService bookedTicketService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private BookedTicketDetailRepository bookedTicketDetailRepository;

	/*
		Input {
			id football field
			id customer
			start time
			end time
		}
	 */
	@PostMapping("/fields/order")
	public ResponseEntity<?> orderField(@Valid @RequestBody FieldOrderForm fieldOrderForm) {
		System.out.println(fieldOrderForm);
		if (!footballFieldService.existsById(fieldOrderForm.getField_id())) {
			return new ResponseEntity<>(new ResponseMessage("The field is not " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

			if (!customerService.existsByPhoneNumber(fieldOrderForm.getPhone())) {
				return new ResponseEntity<>(new ResponseMessage("The customer is not " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}

		if (footballFieldService.check_available_space(fieldOrderForm.getStart_time(), fieldOrderForm.getEnd_time())) {
			return new ResponseEntity<>(new ResponseMessage("The field is ordered",
			HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		int hours =
		fieldOrderForm.getEnd_time().getHour() - fieldOrderForm.getStart_time().getHour();
		int minutes =
		fieldOrderForm.getEnd_time().getMinute() - fieldOrderForm.getStart_time().getMinute();
		if(hours * 60 + minutes < 60) {
			return new ResponseEntity<>(new ResponseMessage("The field must be " +
			"ordered at least 60 minutes",
			HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		LocalDateTime now = LocalDateTime.now();
		boolean isStartTimeBeforeNow = fieldOrderForm.getStart_time().isBefore(now);
		boolean isEndTimeBeforeNow = fieldOrderForm.getEnd_time().isBefore(now);
		if(isStartTimeBeforeNow || isEndTimeBeforeNow) {
			return new ResponseEntity<>(new ResponseMessage("Please choose the next" +
			" time from now",
			HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		Long footballFieldId = fieldOrderForm.getField_id();

		FootballField footballField =
		footballFieldService.findById(footballFieldId).get();

		int hourStartTime = fieldOrderForm.getStart_time().getHour();
		boolean hasFieldFeeWeight =
		hourStartTime >= 18 && hourStartTime <= 22 || hourStartTime >= 5 && hourStartTime <= 8;
		System.out.println(hasFieldFeeWeight);
		double fieldFeeWeight = 0;
		int totalPrice = (int) Math.floor(footballField.getPrice() * (hours + minutes / 60));
		if(hasFieldFeeWeight) {
			fieldFeeWeight = 0.2;
			totalPrice = (int) Math.floor(totalPrice + totalPrice * fieldFeeWeight);
		}

		Customer customer =
		customerService.findCustomerByPhone(fieldOrderForm.getPhone()).get();

		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long currentAccountId = userPrincipal.getId();

		Employee employee = null;

		Optional<Employee> optionalEmployee =
		employeeRepository.findEmployeeByAccount_Id(currentAccountId);

		if (optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
			employee = employeeRepository.findEmployeeByAccount_Id(currentAccountId).get();
		}

		BookedTicket receipt =
		BookedTicket.builder().customer(customer).employee(employee).paymentStatus(PaymentStatus.PROCESSING).totalPrice(totalPrice).build();

		BookedTicketDetail receiptDetail =
		BookedTicketDetail.builder()
		.id(BookedTicketDetailKey.builder().bookedTicketId(receipt.getId()).footballFieldId(footballFieldId).build())
		.bookedTicket(receipt)
		.footballField(footballField)
		.deposit(0)
		.startTime(fieldOrderForm.getStart_time())
		.endTime(fieldOrderForm.getEnd_time())
		.build();

		BookedTicket receiptBody = bookedTicketService.save(receipt);
		BookedTicketDetail receiptDetailBody = bookedTicketService.save(receiptDetail);
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("receipt_id", receiptBody.getId());
		response.put("total_price", totalPrice);
		response.put("payment_status", PaymentStatus.PROCESSING.toString());
		response.put("created_at", receiptBody.getCreatedAt());
		response.put("deposit", receiptDetailBody.getDeposit());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/played")
	public ResponseEntity<?> getPlayedBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();

		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getPlayedBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "played");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/playing")
	public ResponseEntity<?> getPlayingBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();
		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getPlayingBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "playing");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/will-play")
	public ResponseEntity<?> getWillPlayBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();
		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getWillPlayBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "will-play");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/week")
	public ResponseEntity<?> getBookedFieldByWeek(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
		Long field_id = bookedTicketForm.getField_id();

		LocalDate currentDay = LocalDate.now();

		int dayOfWeek = currentDay.getDayOfWeek().getValue();

		Map<Integer, Integer> days = getDaysOfWeek();

		int neededDay = days.get(DayOfWeek.SUNDAY.getValue()) - days.get(dayOfWeek);

		LocalDateTime firstDayOfWeek = currentDay.minusDays(days.get(dayOfWeek)).atStartOfDay();
		LocalDateTime lastDayOfWeek = currentDay.plusDays(neededDay).atStartOfDay();

		List<BookedTicketDetail> bookedTicketDetailList = bookedTicketService.getBookedTicketDetailByWeek(firstDayOfWeek, lastDayOfWeek, field_id);

		List<Map<String, Object>> fieldBooked = new ArrayList<>();
		for (BookedTicketDetail btd:bookedTicketDetailList) {
			if(!btd.isCanceled()) {
				Map<String, Object> data = new HashMap<>();
				data.put("field_id", btd.getFootballField().getId());
				data.put("start", btd.getStartTime());
				data.put("end", btd.getEndTime());
				data.put("id", btd.getId().getBookedTicketId());
				if(btd.getBookedTicket().getCustomer() != null) {
					data.put("phone",
					btd.getBookedTicket().getCustomer().getAccount().getPhone());
				}
				data.put("total_price", btd.getBookedTicket().getTotalPrice());
				fieldBooked.add(data);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("data", fieldBooked);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/field/cancel/{id}")
	public ResponseEntity<?> cancelOrderField(@PathVariable Long id) {
		if(id == null) {
			return new ResponseEntity<>(new ResponseMessage("The given id must not " +
			"be null",
			HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
		}

		Optional<BookedTicketDetail> optionalBookedTicketDetail =
		bookedTicketDetailRepository.findByBookedTicket_Id(id);

		if(!optionalBookedTicketDetail.isPresent() || optionalBookedTicketDetail.isEmpty()) {
			return new ResponseEntity<>(new ResponseMessage("Cannot find this field",
			HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
		}

		BookedTicketDetail bookedTicketDetail = optionalBookedTicketDetail.get();

		LocalDateTime startTime = bookedTicketDetail.getStartTime();
		boolean isPossibleToCancel =
		startTime.isAfter(ChronoLocalDateTime.from(LocalDateTime.now()).plus(2,
		ChronoUnit.HOURS));

		System.out.println(isPossibleToCancel);
		System.out.println(ChronoLocalDateTime.from(LocalDateTime.now()).plus(2,
		ChronoUnit.HOURS));
		if(!isPossibleToCancel) {
			return new ResponseEntity<>(new ResponseMessage("You mustn't cancel " +
			"order this field before two hours from start time",
			HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		bookedTicketDetail.setCanceled(true);
		bookedTicketDetailRepository.save(bookedTicketDetail);
		return new ResponseEntity<>(new ResponseMessage("Cancel order field " +
		"successfully",
		HttpStatus.OK.value()), HttpStatus.OK);
	}


	public Map<Integer, Integer> getDaysOfWeek() {
		Map<Integer, Integer> days = new HashMap<>();
		days.put(DayOfWeek.MONDAY.getValue(), 0);
		days.put(DayOfWeek.TUESDAY.getValue(), 1);
		days.put(DayOfWeek.WEDNESDAY.getValue(), 2);
		days.put(DayOfWeek.THURSDAY.getValue(), 3);
		days.put(DayOfWeek.FRIDAY.getValue(), 4);
		days.put(DayOfWeek.SATURDAY.getValue(), 5);
		days.put(DayOfWeek.SUNDAY.getValue(), 6);
		return days;
	}
}
