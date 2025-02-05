package muit.backend.service.ticketService;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.ticketDTO.TicketRequestDTO;
import muit.backend.dto.ticketDTO.TicketResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberTicketService {

    public TicketResponseDTO.MemberTicketResponseDTO createMemberTicket(Member member, Long amateurTicketId, TicketRequestDTO requestDTO);

    public TicketResponseDTO.AmateurShowForTicketDTO getTicketInfo(Long amateurShowId, Member member);

    public List<TicketResponseDTO.SelectionTicketInfoDTO> getSelectionInfo(Long selectionTicketId);

    public TicketResponseDTO.CancelRequestTicketDTO cancelTicketReservation (Member member, Long memberTicketId);

    public TicketResponseDTO.MyPageTicketDTO getMyTicket(Member member,Long memberTicketId);

    public TicketResponseDTO.MyPageTicketListDTO getMyTicketList(Member member, ReservationStatus reservationStatus);

    }
