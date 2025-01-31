package muit.backend.service.theatreService;

import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.Floor;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.dto.sectionDTO.SectionRequestDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TheatreService {

    public TheatreResponseDTO.TheatreResultListDTO findTheatreByName(String theatreName);

    public SectionResponseDTO.SectionResultDTO getSection(Long theatreId, SectionType sectionType);

    public Theatre createTheatre(String kopisTheatreId, Musical musical);

    public ManageViewResponseDTO.AdminTheatreResultListDTO getTheatres(Pageable pageable);

    public TheatreResponseDTO.AdminTheatreDetailDTO getTheatreDetail(Long theatreId);

    public TheatreResponseDTO.TheatreResultDTO uploadTheatrePic(Long theatreId, MultipartFile img);

    public SectionResponseDTO.SectionResultDTO createSection(Long theatreId, SectionRequestDTO.SectionCreateDTO requestDTO, MultipartFile img);

    public SectionResponseDTO.SectionResultDTO editSection(Long sectionId, SectionRequestDTO.SectionCreateDTO requestDTO, MultipartFile img);

    public TheatreResponseDTO.AdminTheatreSectionListDTO getTheatreSections(Long theatreId);

    public SectionResponseDTO.FloorResultDTO getFloor(Long theatreId, Floor floor);
}
