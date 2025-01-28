package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.amateurTicketDTO.AmateurTicketRequestDTO;
import muit.backend.dto.adminDTO.amateurTicketDTO.AmateurTicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AmateurTicketService {

    public Page<AmateurTicketResponseDTO.AmateurTicketResultListDTO> getAllTickets(Pageable pageable,
                                                                                   String keyword,
                                                                                   Set<String> selectedFields);

    public AmateurTicketResponseDTO.AmateurTicketResultDTO getTicket(Long amateurShowId);

    public AmateurTicketResponseDTO.AmateurTicketResultDTO updateTicket(Long amateurShowId, AmateurTicketRequestDTO.AmateurTicketUpdateDTO requestDTO);
}
