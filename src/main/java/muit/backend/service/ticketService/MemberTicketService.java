package muit.backend.service.ticketService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.ticketDTO.MemberTicketRequestDTO;
import muit.backend.dto.ticketDTO.MemberTicketResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberTicketService {

    public MemberTicketResponseDTO createMemberTicket(Member member, MemberTicketRequestDTO requestDTO);
}
