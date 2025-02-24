package muit.backend.converter;

import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisTheatreResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.dto.theatreDTO.TheatreRequestDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TheatreConverter {
    //DTO -> Entity
    public static Theatre toTheatre(TheatreRequestDTO.TheatreCreateDTO theatreCreateDTO, Musical musical) {
        return Theatre.builder()
                .kopisTheatreId(theatreCreateDTO.getKopisTheatreId())
                .address(theatreCreateDTO.getAddress())
                .name(theatreCreateDTO.getName())
                .relateUrl(theatreCreateDTO.getRelateUrl())
                .musical(musical)
                .build();
    }

   public static TheatreResponseDTO.TheatreResultDTO toTheatreResultDTO(Theatre theatre) {
       return TheatreResponseDTO.TheatreResultDTO.builder()
               .id(theatre.getId())
               .theatreName(theatre.getName())
               .address(theatre.getAddress())
               .theatrePic(theatre.getTheatrePic())
               .build();
   }

   public static TheatreResponseDTO.TheatreResultListDTO toTheatreResultListDTO(List<Theatre> theatres) {

       List<TheatreResponseDTO.TheatreResultDTO> theatreResultsDTO = theatres.stream()
               .map(TheatreConverter::toTheatreResultDTO).toList();

       String msg = "검색 결과 " + theatres.size() + "건";
       if(theatres.isEmpty()) {msg = "검색 결과가 존재하지 않습니다";}
       return TheatreResponseDTO.TheatreResultListDTO.builder()
                .message(msg)
                .theatreResults(theatreResultsDTO)
                .build();
   }

   public static TheatreRequestDTO.TheatreCreateDTO convertKopisDTOToTheatreCreateDTO(KopisTheatreResponseDTO.KopisTheatreDTO kopisTheatreDTO) {
        return TheatreRequestDTO.TheatreCreateDTO.builder()
                .name(kopisTheatreDTO.getFcltynm())
                .kopisTheatreId(kopisTheatreDTO.getMt10id())
                .address(kopisTheatreDTO.getAdres())
                .relateUrl(kopisTheatreDTO.getRelateurl())
                .build();
   }

   public static TheatreResponseDTO.AdminTheatreDetailDTO toAdminTheatreDetailDTO(Theatre theatre) {
        return TheatreResponseDTO.AdminTheatreDetailDTO.builder()
                .theatreId(theatre.getId())
                .theatreName(theatre.getName())
                .address(theatre.getAddress())
                .musicalId(theatre.getMusical().getId())
                .musicalName(theatre.getMusical().getName())
                .theatrePic(theatre.getTheatrePic())
                .allSeatImg(theatre.getAllSeatImg())
                .build();
   }

   public static TheatreResponseDTO.AdminTheatreSectionDTO toAdminTheatreSectionDTO(Section section) {
        return TheatreResponseDTO.AdminTheatreSectionDTO.builder()
                .sectionType(section.getSectionType())
                .floor(section.getFloor())
                .seatRange(section.getSeatRange())
                .viewDetail(section.getViewDetail())
                .isViewPic(section.getViewPic()!=null)
                .build();
   }

   public static TheatreResponseDTO.AdminTheatreSectionListDTO toAdminTheatreSectionListDTO(Theatre theatre, List<TheatreResponseDTO.AdminTheatreSectionDTO> sections) {
        if(sections==null) {
            sections = new ArrayList<>();
       }
        return TheatreResponseDTO.AdminTheatreSectionListDTO.builder()
                .theatreId(theatre.getId())
                .theatreName(theatre.getName())
                .isTheatrePic(theatre.getTheatrePic()!=null)
                .theatreSections(sections)
                .build();
   }

}
