package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.dto.request.ItemOrderForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.EquipmentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.repositories.ItemRepository;
import s1mple.dlowji.ffms_refactor.repositories.ServiceReceiptDetailRepository;
import s1mple.dlowji.ffms_refactor.repositories.ServicesReceiptRepository;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.ICustomerService;
import s1mple.dlowji.ffms_refactor.services.ItemService;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	private ICustomerService iCustomerService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ServiceReceiptDetailRepository serviceReceiptDetailRepository;

	@Autowired
	private ServicesReceiptRepository servicesReceiptRepository;

	@PostMapping("/item/sell")
	public ResponseEntity<?> buyItem(@Valid @RequestBody ItemOrderForm itemOrderForm) {
		Customer customer = null;
		if (itemOrderForm.getPhone() != null) {
			Optional<Customer> optionalCustomer =
			iCustomerService.findCustomerByPhone(itemOrderForm.getPhone());
			if (optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
				customer = optionalCustomer.get();
			}
		}

		List<HashMap<String, Long>> items = itemOrderForm.getItems();

		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long currentAccountId = userPrincipal.getId();

		Employee employee = null;

		if (employeeRepository.findEmployeeByAccount_Id(currentAccountId).isPresent()) {
			employee = employeeRepository.findEmployeeByAccount_Id(currentAccountId).get();
		}

		Customer finalCustomer = customer;
		Employee finalEmployee = employee;
		AtomicInteger totalPrice = new AtomicInteger();
		List<ServiceReceiptDetail> serviceReceiptDetails = new ArrayList<>();
		Map<Item, Integer> itemsBuy = new HashMap<>();


		for (HashMap<String, Long> item : items) {
			Item itemBuy = itemRepository.findItemById(item.get("id"));

			int currentQuantity = itemBuy.getQuantity();
			int quantityBuy = Math.toIntExact(item.get("quantity"));
			if (quantityBuy > currentQuantity) {
				return new ResponseEntity<>(new ResponseMessage("The quantity is not " +
				"enough. Please try again!", HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
			}
			totalPrice.set(totalPrice.get() + (int) (itemService.findSellPrice(itemBuy.getId()) * item.get(
			"quantity")));
			if (currentQuantity - quantityBuy == 0) {
				itemBuy.setStatus(EquipmentStatus.SOLD_OUT);
			}
			itemBuy.setQuantity(currentQuantity - quantityBuy);
			itemsBuy.put(itemBuy, quantityBuy);
		}

		ServiceReceipt serviceReceipt = ServiceReceipt.builder()
		.paymentStatus(PaymentStatus.PROCESSING)
		.user(finalCustomer)
		.employee(finalEmployee)
		.totalPrice(totalPrice.get())
		.build();

		servicesReceiptRepository.save(serviceReceipt);

		itemsBuy.forEach((itemBuy, quantityBuy) -> {
			ServiceReceiptDetail serviceReceiptDetail = ServiceReceiptDetail.builder()
			.item(itemBuy)
			.serviceReceipt(serviceReceipt)
			.quantity(quantityBuy)
			.id(ServiceReceiptDetailKey.builder().serviceReceiptId(serviceReceipt.getId()).itemId(itemBuy.getId()).build())
			.build();
			serviceReceiptDetails.add(serviceReceiptDetail);
			serviceReceiptDetailRepository.save(serviceReceiptDetail);
			itemService.save(itemBuy);
		});

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("receipt_id", serviceReceipt.getId());
		response.put("total_price", totalPrice);
		response.put("payment_status", PaymentStatus.PROCESSING.toString());
		response.put("created_at", serviceReceipt.getCreatedAt());

		return ResponseEntity.ok(response);
	}
}
