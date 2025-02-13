package muit.backend.converter;

import muit.backend.domain.entity.amateur.AmateurTicket;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.dto.ticketDTO.TicketResponseDTO;

public class MemberTicketConverter {

    public static TicketResponseDTO.MemberTicketResponseDTO toTicketDTO(MemberTicket memberTicket) {
        return new TicketResponseDTO.MemberTicketResponseDTO(
                memberTicket.getId(),
                memberTicket.getMember().getId(),
                memberTicket.getAmateurTicket().getId(),
                memberTicket.getQuantity(),
                memberTicket.getTotalPrice(),
                memberTicket.getReservationTime(),
                memberTicket.getAccountName(),
                memberTicket.getReservationStatus()
        );
    }

    public static MemberTicket toEntity(Member member, AmateurTicket amateurTicket, int quantity) {
        return MemberTicket.builder()
                .member(member)
                .amateurTicket(amateurTicket)
                .quantity(quantity)
                .totalPrice(amateurTicket.getPrice() * quantity)
                .build();
    }
}
