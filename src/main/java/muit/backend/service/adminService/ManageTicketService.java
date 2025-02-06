package muit.backend.service.adminService;

import jakarta.validation.constraints.NotNull;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.adminDTO.manageTicketDTO.ManageTicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageTicketService {

    public Page<ManageTicketResponseDTO.ManageTicketResultListDTO> getAllTickets(Pageable pageable, String keyword, Set<String> selectedFields);

    public ManageTicketResponseDTO.ManageTicketResultDTO getTicket(Long memberTicketId);

    public ManageTicketResponseDTO.ManageTicketResultDTO updateTicket(Long memberTicketId, @NotNull ReservationStatus reservationStatus);
}