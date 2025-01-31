package muit.backend.converter;

import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.sectionDTO.SectionRequestDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SectionConverter {
    public static Section toSection(Theatre theatre, SectionRequestDTO.SectionCreateDTO sectionCreateDTO) {
         return Section.builder()
                .theatre(theatre)
                .sectionType(sectionCreateDTO.getSectionType())
                .floor(sectionCreateDTO.getFloor())
                .seatRange(sectionCreateDTO.getSeatRange())
                .viewDetail(sectionCreateDTO.getViewDetail())
                .build();
    }

    public static SectionResponseDTO.SectionResultDTO toSectionResultDTO(Section section) {
        return SectionResponseDTO.SectionResultDTO.builder()
                .allSeatImg(section.getTheatre().getAllSeatImg())
                .theatreId(section.getTheatre().getId())
                .theatreName(section.getTheatre().getName())
                .address(section.getTheatre().getAddress())
                .musicalId(section.getTheatre().getMusical().getId())
                .musicalName(section.getTheatre().getMusical().getName())
                .sectionType(section.getSectionType())
                .floor(section.getFloor())
                .seatRange(section.getSeatRange())
                .viewPic(section.getViewPic())
                .viewDetail(section.getViewDetail())
                .build();
    }

    public static SectionResponseDTO.FloorResultDTO toFloorResultDTO(Theatre theatre, List<SectionType> types) {

        return SectionResponseDTO.FloorResultDTO.builder()
                .allSeatImg(theatre.getAllSeatImg())
                .theatreId(theatre.getId())
                .theatreName(theatre.getName())
                .address(theatre.getAddress())
                .musicalId(theatre.getMusical().getId())
                .musicalName(theatre.getMusical().getName())
                .sectionTypes(types)
                .build();
    }
}
