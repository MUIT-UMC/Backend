package muit.backend.service.ticketService;


import lombok.RequiredArgsConstructor;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.ticketDTO.MemberTicketRequestDTO;
import muit.backend.dto.ticketDTO.MemberTicketResponseDTO;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.MemberTicketRepository;
import muit.backend.repository.amateurRepository.AmateurTicketRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberTicketServiceImpl implements MemberTicketService {
    private final MemberTicketRepository memberTicketRepository;
    private final MemberRepository memberRepository;
    private final AmateurTicketRepository  amateurTicketRepository;

//    @Transactional
//    public MemberTicketResponseDTO createMemberTicket(MemberTicketRequestDTO memberTicketRequestDTO) {
//        Member member = memberRepository.findById(memberTicketRequestDTO.getMemberId()).orElse(null);
//    }
}
