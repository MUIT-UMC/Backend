package muit.backend.service.musicalService;

import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MusicalService {

    //특정 뮤지컬 조회
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicId);

    //뮤지컬 생성
    public void createMusical(String kopisMusicalId);

    public MusicalResponseDTO.MusicalHomeListDTO getFiveMusicals();

    public MusicalResponseDTO.MusicalHomeListDTO getAllHotMusicals(Integer page);

    public MusicalResponseDTO.MusicalOpenListDTO getFiveOpenMusicals();

    public MusicalResponseDTO.MusicalOpenListDTO getAllOpenMusicals(Integer page);

    public MusicalResponseDTO.MusicalHomeListDTO findMusicalsByName(String musicalName);
}
