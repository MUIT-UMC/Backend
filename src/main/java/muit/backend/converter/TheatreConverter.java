package muit.backend.converter;

import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisTheatreResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.dto.theatreDTO.TheatreRequestDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        return TheatreResponseDTO.TheatreResultListDTO.builder()
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

}
