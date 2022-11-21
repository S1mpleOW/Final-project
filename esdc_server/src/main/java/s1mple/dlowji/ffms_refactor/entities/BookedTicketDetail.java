package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "booked_ticket_detail")
public class BookedTicketDetail {
    @EmbeddedId
    private BookedTicketDetailKey id;

    @JoinColumn(name = "BOOKED_TICKET_ID")
    @MapsId("bookedTicketId")
    @ManyToOne
    private BookedTicket bookedTicket;

    @JoinColumn(name = "FOOTBALL_FIELD_ID")
    @MapsId("footballFieldId")
    @ManyToOne
    private FootballField footballField;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @Column(name = "DEPOSIT")
    private int deposit;

    @Column(name = "ORDER_DATE")
    @CreationTimestamp
    private ZonedDateTime orderDate;

    @Column(name = "IS_CANCELED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isCanceled;
}
