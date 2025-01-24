package muit.backend.service;

import jakarta.validation.constraints.NotNull;
import muit.backend.domain.enums.AmateurStatus;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowRequestDTO;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageAmateurShowService {

    public Page<ManageAmateurShowResponseDTO.ResultListDTO> getAllAmateurShows(Pageable pageable, String keyword, Set<String> selectedFields);
    public ManageAmateurShowResponseDTO.ResultDTO getAmateurShow(Long amateurShowId);
    public ManageAmateurShowResponseDTO.ResultDTO updateAmateurShow(Long amateurShowId, ManageAmateurShowRequestDTO.UpdateDTO requestDTO);
    public ManageAmateurShowResponseDTO.DecideDTO decideAmateurShow(Long amateurShowId, @NotNull AmateurStatus amateurStatus, ManageAmateurShowRequestDTO.DecideDTO requestDTO);
}
