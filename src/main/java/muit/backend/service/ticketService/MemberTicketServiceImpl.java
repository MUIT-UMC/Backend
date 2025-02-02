package muit.backend.service.ticketService;


import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.MemberTicketConverter;
import muit.backend.domain.entity.amateur.AmateurTicket;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.ticketDTO.MemberTicketRequestDTO;
import muit.backend.dto.ticketDTO.MemberTicketResponseDTO;
import muit.backend.repository.MemberRepository;
import muit.backend.repository.MemberTicketRepository;
import muit.backend.repository.amateurRepository.AmateurTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberTicketServiceImpl implements MemberTicketService {
    private final MemberTicketRepository memberTicketRepository;
    private final MemberRepository memberRepository;
    private final AmateurTicketRepository  amateurTicketRepository;

    @Override
    @Transactional
    public MemberTicketResponseDTO createMemberTicket(Member member, MemberTicketRequestDTO requestDTO) {
        // 티켓 조회
        AmateurTicket amateurTicket = amateurTicketRepository.findById(requestDTO.getAmateurTicketId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.AMATEUR_TICKET_NOT_FOUND));

        if (requestDTO.getQuantity() <= 0) {
            throw new GeneralException(ErrorStatus.MEMBER_TICKET_QUANTITY);
        }

        int totalPrice = amateurTicket.getPrice() * requestDTO.getQuantity();

        MemberTicket memberTicket = MemberTicket.builder()
                .member(member)
                .amateurTicket(amateurTicket)
                .quantity(requestDTO.getQuantity())
                .totalPrice(totalPrice)
                .reservationStatus(ReservationStatus.RESERVE_AWAIT)
                .build();
        memberTicketRepository.save(memberTicket);

        return MemberTicketConverter.toTicketDTO(memberTicket);
    }




}
