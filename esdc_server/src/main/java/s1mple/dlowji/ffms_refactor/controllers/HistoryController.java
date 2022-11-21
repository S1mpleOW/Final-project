package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.dto.response.*;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;
import s1mple.dlowji.ffms_refactor.entities.ServiceReceipt;
import s1mple.dlowji.ffms_refactor.repositories.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
public class HistoryController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private BookedTicketRepository bookedTicketRepository;

	@Autowired
	private ServicesReceiptRepository servicesReceiptRepository;

	@Autowired
	private ImportRepository importRepository;

	@GetMapping("/history-transaction/customer/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
	public ResponseEntity<?> getTransactionCustomer(@PathVariable("id") Long id, Pageable pageable) {
		if (!customerRepository.existsById(id)) {
			return new ResponseEntity<>(new ResponseMessage("The customer's id is " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		List<BookedTicket> bookedTickets =
		bookedTicketRepository.findBookedTicketsByCustomer_Id(id);

		List<ServiceReceipt> serviceReceipts =
		servicesReceiptRepository.findServiceReceiptsByUser_Id(id);

		List<Transaction> transactions = new ArrayList<>();

		bookedTickets.forEach(bookedTicket -> {
			ReceiptHistoryDTO bookedTicketDTO =
			ReceiptHistoryDTO.builder()
			.totalPrice(bookedTicket.getTotalPrice())
			.note(bookedTicket.getNote())
			.paymentStatus(bookedTicket.getPaymentStatus())
			.build();
			transactions.add(Transaction.builder().bookedTicket(bookedTicketDTO).createdAt(bookedTicket.getCreatedAt()).type("BOOKED_RECEIPT").build());
		});

		serviceReceipts.forEach(serviceReceipt -> {
			ReceiptHistoryDTO serviceReceiptDTO = ReceiptHistoryDTO.builder()
			.paymentStatus(serviceReceipt.getPaymentStatus())
			.note(serviceReceipt.getNote())
			.totalPrice(serviceReceipt.getTotalPrice())
			.build();
			transactions.add(Transaction.builder().serviceReceipt(serviceReceiptDTO).createdAt(serviceReceipt.getCreatedAt()).type("SERVICE_RECEIPT").build());
		});

		transactions.sort(Comparator.comparing(Transaction::getCreatedAt).reversed());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), transactions.size());
		List<Transaction> subList = start >= end ? new ArrayList<>() : transactions.subList(start, end);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("data", new PageImpl<>(subList, pageable, transactions.size()));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/history-transaction/employee/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<?> getTransactionEmployee(@PathVariable("id") Long id, Pageable pageable) {
		if (!employeeRepository.existsById(id)) {
			return new ResponseEntity<>(new ResponseMessage("The employee's id is " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		List<ImportReceipt> importReceipts =
		importRepository.findImportReceiptsByEmployee_Id(id);

		List<BookedTicket> bookedTickets =
		bookedTicketRepository.findBookedTicketsByEmployee_Id(id);

		List<ServiceReceipt> serviceReceipts =
		servicesReceiptRepository.findServiceReceiptsByEmployee_Id(id);


		List<Transaction> transactions = new ArrayList<>();

		bookedTickets.forEach(bookedTicket -> {
			ReceiptHistoryDTO bookedTicketDTO =
			ReceiptHistoryDTO.builder()
			.totalPrice(bookedTicket.getTotalPrice())
			.note(bookedTicket.getNote())
			.paymentStatus(bookedTicket.getPaymentStatus())
			.build();
			transactions.add(Transaction.builder().bookedTicket(bookedTicketDTO).createdAt(bookedTicket.getCreatedAt()).type("BOOKED_RECEIPT").build());
		});

		serviceReceipts.forEach(serviceReceipt -> {
			ReceiptHistoryDTO serviceReceiptDTO = ReceiptHistoryDTO.builder()
			.paymentStatus(serviceReceipt.getPaymentStatus())
			.note(serviceReceipt.getNote())
			.totalPrice(serviceReceipt.getTotalPrice())
			.build();
			transactions.add(Transaction.builder().serviceReceipt(serviceReceiptDTO).createdAt(serviceReceipt.getCreatedAt()).type("SERVICE_RECEIPT").build());
		});


		importReceipts.forEach(importReceipt -> {
			ReceiptHistoryDTO importReceiptDTO = ReceiptHistoryDTO.builder()
			.paymentStatus(importReceipt.getPaymentStatus())
			.note(importReceipt.getNote())
			.totalPrice(importReceipt.getTotalPrice())
			.build();
			transactions.add(Transaction.builder().importReceipt(importReceiptDTO).createdAt(importReceipt.getCreatedAt()).type("IMPORT_RECEIPT").build());
		});

		transactions.sort(Comparator.comparing(Transaction::getCreatedAt).reversed());

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), transactions.size());
		List<Transaction> subList = start >= end ? new ArrayList<>() : transactions.subList(start, end);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("data", new PageImpl<>(subList, pageable, transactions.size()));
		return ResponseEntity.ok(response);
	}
}
