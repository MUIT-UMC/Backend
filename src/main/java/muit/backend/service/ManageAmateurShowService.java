package muit.backend.service;

import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageAmateurShowService {

    public Page<ManageAmateurShowResponseDTO.ResultListDTO> getAllAmateurShows(Pageable pageable, String keyword, Set<String> selectedFields);
    public ManageAmateurShowResponseDTO.ResultDTO getAmateurShow(Long amateurShowId);
}
