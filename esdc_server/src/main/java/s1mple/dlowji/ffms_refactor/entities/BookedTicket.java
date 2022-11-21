package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.converters.PaymentConverter;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booked_ticket")
public class BookedTicket extends AbstractEntity {
    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "PAYMENT_STATUS")
    @Convert(converter = PaymentConverter.class)
    private PaymentStatus paymentStatus;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    private Employee employee;

    @OneToMany(mappedBy = "bookedTicket", fetch = FetchType.LAZY)
    private List<BookedTicketDetail> bookedTicketDetails;
}
