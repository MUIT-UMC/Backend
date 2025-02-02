package muit.backend.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberTicketResponseDTO {
    private Long memberTicketId;
    private Long memberId;
    private Long amateurTicketId;
    private int quantity;
    private int totalPrice;
    private LocalDateTime date;
    private ReservationStatus reservationStatus;



}
