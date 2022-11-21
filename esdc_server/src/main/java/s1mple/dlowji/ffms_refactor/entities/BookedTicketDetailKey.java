package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedTicketDetailKey implements Serializable {
    @Column(name = "FOOTBALL_FIELD_ID")
    private Long footballFieldId;

    @Column(name = "BOOKED_TICKET_ID")
    private Long bookedTicketId;
}
