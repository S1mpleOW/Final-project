package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "service_receipt")
@ToString
public class ServiceReceipt extends AbstractEntity {
    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private Customer user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToMany(mappedBy = "serviceReceipt", fetch = FetchType.LAZY)
    private List<ServiceReceiptDetail> serviceReceiptDetails;

}
