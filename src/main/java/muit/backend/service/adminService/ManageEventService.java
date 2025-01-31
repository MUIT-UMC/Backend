package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageEventService {

    public Page<ManageEventResponseDTO.ManageEventResultListDTO> getAllMusicals(Pageable pageable, String keyword, Set<String> selectedFields);

    public ManageEventResponseDTO.ManageEventResultDTO getEvent(Long musicalId);
}