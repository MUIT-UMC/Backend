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

    public Page<ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO> getAllAmateurShows(Pageable pageable, String keyword, Set<String> selectedFields);
    public ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO getAmateurShow(Long amateurShowId);
    public ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO updateAmateurShow(Long amateurShowId, ManageAmateurShowRequestDTO.ManageAmateurShowUpdateDTO requestDTO);
    public ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO decideAmateurShow(Long amateurShowId, @NotNull AmateurStatus amateurStatus, ManageAmateurShowRequestDTO.ManageAmateurShowDecideDTO requestDTO);
}
