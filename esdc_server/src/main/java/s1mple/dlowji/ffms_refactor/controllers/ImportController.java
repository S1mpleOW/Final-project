package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.ImportItemsForm;
import s1mple.dlowji.ffms_refactor.dto.request.OrderMoreProductDTO;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.EquipmentStatus;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.repositories.*;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;
import s1mple.dlowji.ffms_refactor.services.ItemService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImportController {

	@Autowired
	private ImportRepository importRepository;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ImportDetailRepository importDetailRepository;
    /*
		Input {
			id supplier
			image
			import price
			category
			name
			note
			quantity
			status
			unit
			delivery date
		}
	 */

	@PostMapping("/item/order-more")
	public ResponseEntity<?> orderMore(@Valid @RequestBody OrderMoreProductDTO orderMoreProductDTO) {
		if (!itemService.existsById((orderMoreProductDTO.getItemID()))) {
			return new ResponseEntity<>(new ResponseMessage("The supplier is not " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		Item item = itemService.findById(orderMoreProductDTO.getItemID());
		int importPrice = item.getImportPrice();
		int moreQuantity = orderMoreProductDTO.getQuantity();
		item.setQuantity(moreQuantity + item.getQuantity());
		if (item.getStatus().equals(EquipmentStatus.SOLD_OUT)) {
			item.setStatus(EquipmentStatus.AVAILABLE);
		}

		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long currentAccountId = userPrincipal.getId();

		Employee employee = null;

		if (employeeRepository.findEmployeeByAccount_Id(currentAccountId).isPresent()) {
			employee = employeeRepository.findEmployeeByAccount_Id(currentAccountId).get();
		}

		int totalPrice = importPrice * moreQuantity;
		ImportReceipt receipt =
		ImportReceipt.builder()
		.note(orderMoreProductDTO.getNote())
		.paymentStatus(PaymentStatus.PROCESSING)
		.totalPrice(totalPrice)
		.employee(employee)
		.build();

		//build import receipt detail
		ImportReceiptDetail receiptDetail =
		ImportReceiptDetail.builder()
		.id(ImportReceiptDetailKey.builder().importReceiptId(receipt.getId()).itemId(item.getId()).build())
		.importReceipt(receipt)
		.item(item)
		.deliveryDate(orderMoreProductDTO.getDelivery_date())
		.quantity(orderMoreProductDTO.getQuantity())
		.build();


		itemService.save(item);
		ImportReceipt receiptBody = importRepository.save(receipt);
		importDetailRepository.save(receiptDetail);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("item_id", item.getId());
		response.put("receipt_id", receiptBody.getId());
		response.put("total_price", totalPrice);
		response.put("payment_status", PaymentStatus.PROCESSING.toString());
		response.put("created_at", receiptBody.getCreatedAt());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/item/order")
	public ResponseEntity<?> importItems(@Valid @RequestBody ImportItemsForm importItemsForm) {
		if (!supplierRepository.existsById((importItemsForm.getSupplier_id()))) {
			return new ResponseEntity<>(new ResponseMessage("The supplier is not " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		int importPrice = importItemsForm.getImport_price();
		int quantity = importItemsForm.getQuantity();
		int totalPrice = importPrice * quantity;

		Authentication authentication =
		SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Long currentAccountId = userPrincipal.getId();

		Employee employee = null;

		if (employeeRepository.findEmployeeByAccount_Id(currentAccountId).isPresent()) {
			employee = employeeRepository.findEmployeeByAccount_Id(currentAccountId).get();
		}
		Supplier supplier = supplierRepository.findById(importItemsForm.getSupplier_id()).get();

		Item item = null;

		if (itemService.existsByNameIgnoreCase((importItemsForm.getName()))) {
			item = itemService.findByName(importItemsForm.getName());
			item.setQuantity(item.getQuantity() + importItemsForm.getQuantity());
		} else {
			//build item
			item =
			Item.builder()
			.name(importItemsForm.getName())
			.status(EquipmentStatus.AVAILABLE)
			.importPrice(importItemsForm.getImport_price())
			.unit(importItemsForm.getUnit())
			.image(importItemsForm.getImage())
			.quantity(importItemsForm.getQuantity())
			.note(importItemsForm.getNote())
			.itemCategory(importItemsForm.getItem_category())
			.supplier(supplier)
			.build();
		}

		//build import receipt
		ImportReceipt receipt =
		ImportReceipt.builder()
		.note(importItemsForm.getNote())
		.paymentStatus(PaymentStatus.PROCESSING)
		.totalPrice(totalPrice)
		.employee(employee)
		.build();

		//build import receipt detail
		ImportReceiptDetail receiptDetail =
		ImportReceiptDetail.builder()
		.id(ImportReceiptDetailKey.builder().importReceiptId(receipt.getId()).itemId(item.getId()).build())
		.importReceipt(receipt)
		.item(item)
		.deliveryDate(importItemsForm.getDelivery_date())
		.quantity(importItemsForm.getQuantity())
		.build();

		itemService.save(item);
		ImportReceipt receiptBody = importRepository.save(receipt);
		importDetailRepository.save(receiptDetail);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("item_id", item.getId());
		response.put("receipt_id", receiptBody.getId());
		response.put("total_price", totalPrice);
		response.put("payment_status", PaymentStatus.PROCESSING.toString());
		response.put("created_at", receiptBody.getCreatedAt());
		return ResponseEntity.ok(response);
	}
}
