package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.manageEventDTO.ManageEventRequestDTO;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageEventService {

    public Page<ManageEventResponseDTO.ManageEventResultListDTO> getAllEvents(Pageable pageable, String keyword, Set<String> selectedFields);

    public ManageEventResponseDTO.ManageEventResultDTO getEvent(Long musicalId);

    public ManageEventResponseDTO.ManageEventResultDTO addEvent(Long musicalId, ManageEventRequestDTO.ManageEventAddDTO requestDTO);

    public ManageEventResponseDTO.ManageEventResultDTO createEvent(ManageEventRequestDTO.ManageEventCreateDTO requestDTO);
}