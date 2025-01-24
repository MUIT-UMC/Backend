package muit.backend.service;

import muit.backend.dto.amateurTicketDTO.AmateurTicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AmateurTicketService {

    public Page<AmateurTicketResponseDTO.ResultListDTO> getAllTickets(Pageable pageable,
                                                                      String keyword,
                                                                      Set<String> selectedFields);
}
