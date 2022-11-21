package s1mple.dlowji.ffms_refactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s1mple.dlowji.ffms_refactor.dto.request.BookedTicketForm;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookedTicketController {
    @Autowired
    private IBookedTicketService bookedTicketService;

    @GetMapping("/booked-price/{year}/{quarter}")
    public ResponseEntity<?> getTotalBookedPriceByQuarter(@PathVariable("year") int year, @PathVariable("quarter") int quarter) {

        List<BookedTicket> bookedTicketList = bookedTicketService.getBookedTicketByQuarter(quarter, year);

        int totalPrice = 0;

        for (BookedTicket receipt:bookedTicketList) {
            totalPrice += receipt.getTotalPrice();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_price", totalPrice);
        response.put("year", year);
        response.put("quarter", quarter);

        return ResponseEntity.ok(response);
    }
}
