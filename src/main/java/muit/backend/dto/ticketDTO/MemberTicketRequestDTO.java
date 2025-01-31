package muit.backend.dto.ticketDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberTicketRequestDTO {
    private Long memberId;
    private Long amateurTicketId;
    private int quantity;
}
