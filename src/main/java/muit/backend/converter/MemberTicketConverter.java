package muit.backend.converter;

import muit.backend.domain.entity.amateur.AmateurTicket;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.dto.ticketDTO.MemberTicketRequestDTO;
import muit.backend.dto.ticketDTO.MemberTicketResponseDTO;

public class MemberTicketConverter {

    public MemberTicketResponseDTO toTicketDTO(MemberTicket memberTicket) {
        return new MemberTicketResponseDTO(
                memberTicket.getId(),
                memberTicket.getMember().getId(),
                memberTicket.getAmateurTicket().getId(),
                memberTicket.getQuantity(),
                memberTicket.getTotalPrice(),
                memberTicket.getReservationTime(),
                memberTicket.getReservationStatus()
        );
    }

    public MemberTicket toEntity(Member member, AmateurTicket amateurTicket, int quantity) {
        return MemberTicket.builder()
                .member(member)
                .amateurTicket(amateurTicket)
                .quantity(quantity)
                .totalPrice(amateurTicket.getPrice() * quantity)
                .build();
    }
}
