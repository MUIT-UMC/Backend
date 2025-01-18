package muit.backend.service;

import muit.backend.domain.enums.SectionType;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface TheatreService {
    public TheatreResponseDTO.TheatreResultDTO getTheatre(String theatreName);

    public SectionResponseDTO.SectionResultDTO getSection(Long theatreId, SectionType sectionType);
}
