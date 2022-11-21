package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.IEmployeeServices;
import s1mple.dlowji.ffms_refactor.services.IImportReceiptService;
import s1mple.dlowji.ffms_refactor.services.ItemService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAuthority('ADMIN')")
public class StatisticController {

	@Autowired
	private IImportReceiptService importReceiptService;

	@Autowired
	private IBookedTicketService bookedTicketService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private IEmployeeServices iEmployeeServices;

	public static int getNumberOfDaysInMonth(int year,int month)
	{
		// LocalDate object
		LocalDate date = LocalDate.of(year, month, 1);
		return date.lengthOfMonth();
	}

	@GetMapping("/total-employee-salary-each-month")
	public ResponseEntity<?> getTotalSalaryEmployee() {
		double totalPrice = iEmployeeServices.getTotalSalaryEmployeeEachMonth();
		LocalDateTime now = LocalDateTime.now();
		int currentYear = now.getYear();
		int currentMonth = now.getMonthValue();
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", currentYear);
		response.put("month", currentMonth);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/import-price/{year}/{month}")
	public ResponseEntity<?> getImportPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		int totalPrice = importReceiptService.getImportReceiptsByMonth(month, year);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("month", month);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/purchase-price/{year}/{month}")
	public ResponseEntity<?> getPurchasePriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		int totalPrice = itemService.getPurchasePriceByMonth(month, year);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("month", month);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/booked-monthly-price/{year}/{month}")
	public ResponseEntity<?> getBookedPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		int totalPrice = bookedTicketService.getBookedPriceByMonth(month, year);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("month", month);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-price/{year}/{month}")
	public ResponseEntity<?> getTotalPriceByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		int totalPrice = (int) (bookedTicketService.getBookedPriceByMonth(month, year)
		+ itemService.getPurchasePriceByMonth(month, year)
		- importReceiptService.getImportReceiptsByMonth(month, year)
		- iEmployeeServices.getTotalSalaryEmployeeEachMonth());

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("month", month);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-revenue-by-year/{year}")
	public ResponseEntity<?> getTotalRevenueByYear(@PathVariable("year") int year) {
		List<Long> totalPrice = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int totalPriceInMonth = (int) (bookedTicketService.getBookedPriceByMonth(month, year)
			+ itemService.getPurchasePriceByMonth(month, year)
			- importReceiptService.getImportReceiptsByMonth(month, year)
			- iEmployeeServices.getTotalSalaryEmployeeEachMonth());
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-import-by-year/{year}")
	public ResponseEntity<?> getTotalRevenueImportByYear(@PathVariable("year") int year) {
		List<Long> totalPrice = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int totalPriceInMonth = importReceiptService.getImportReceiptsByMonth(month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total imports revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-booked-by-year/{year}")
	public ResponseEntity<?> getTotalRevenueBookedByYear(@PathVariable("year") int year) {
		List<Long> totalPrice = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int totalPriceInMonth = bookedTicketService.getBookedPriceByMonth(month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total hire fields revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-purchase-by-year/{year}")
	public ResponseEntity<?> getTotalRevenuePurchaseByYear(@PathVariable("year") int year) {
		List<Long> totalPrice = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int totalPriceInMonth = itemService.getPurchasePriceByMonth(month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total sale items");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-revenue-by-month/{month}")
	public ResponseEntity<?> getTotalRevenueByMonth(@PathVariable("month") int month) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();

		int numDays = getNumberOfDaysInMonth(year, month);
		List<Long> totalPrice = new ArrayList<>();
		for (int day = 1; day <= numDays; day++) {
			int totalPriceInMonth = bookedTicketService.getBookedPriceByDay(day, month,
			year) + itemService.getPurchasePriceByDay(day, month, year) - importReceiptService.getImportReceiptsByDay(day, month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-import-by-month/{month}")
	public ResponseEntity<?> getTotalRevenueImportByMonth(@PathVariable int month) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int numDays = getNumberOfDaysInMonth(year, month);

		List<Long> totalPrice = new ArrayList<>();
		for (int day = 1; day <= numDays; day++) {
			int totalPriceInMonth =
			importReceiptService.getImportReceiptsByDay(day, month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total imports revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-booked-by-month/{month}")
	public ResponseEntity<?> getTotalRevenueBookedByMonth(@PathVariable int month) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();

		int numDays = getNumberOfDaysInMonth(year, month);

		List<Long> totalPrice = new ArrayList<>();
		for (int day = 1; day <= numDays; day++) {
			int totalPriceInMonth = bookedTicketService.getBookedPriceByDay(day, month,year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total hire fields revenue");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/total-purchase-by-month/{month}")
	public ResponseEntity<?> getTotalRevenuePurchaseByMonth(@PathVariable int month) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int numDays = getNumberOfDaysInMonth(year, month);
		List<Long> totalPrice = new ArrayList<>();
		for (int day = 1; day <= numDays; day++) {
			int totalPriceInMonth = itemService.getPurchasePriceByDay(day, month, year);
			totalPrice.add((long) totalPriceInMonth);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("total_price", totalPrice);
		response.put("year", year);
		response.put("name", "Total sale items");
		return ResponseEntity.ok(response);
	}

}
