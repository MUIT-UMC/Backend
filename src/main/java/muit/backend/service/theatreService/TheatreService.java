package muit.backend.service.theatreService;

import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TheatreService {

    public TheatreResponseDTO.TheatreResultListDTO findTheatreByName(String theatreName);

    public SectionResponseDTO.SectionResultDTO getSection(Long theatreId, SectionType sectionType);

    public Theatre createTheatre(String kopisTheatreId, Musical musical);

    public ManageViewResponseDTO.AdminTheatreResultListDTO getTheatres(Pageable pageable);

    public TheatreResponseDTO.AdminTheatreDetailDTO getTheatreDetail(Long theatreId);
}
