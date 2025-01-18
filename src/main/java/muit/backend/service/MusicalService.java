package muit.backend.service;

import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MusicalService {

    //특정 뮤지컬 조회
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicId);
}
