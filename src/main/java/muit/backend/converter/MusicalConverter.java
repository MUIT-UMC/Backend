package muit.backend.converter;

import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MusicalConverter {

    //DTO -> Entity
    public static Musical toMusical(MusicalRequestDTO.MusicalCreateDTO musicalCreateDTO) {
        return Musical.builder()
                .kopisMusicalId(musicalCreateDTO.getKopisMusicalId())
                .name(musicalCreateDTO.getName())
                .perFrom(musicalCreateDTO.getPerFrom())
                .perTo(musicalCreateDTO.getPerTo())
                .perPattern(musicalCreateDTO.getPerPattern())
                .place(musicalCreateDTO.getPlace())
                .kopisTheatreId(musicalCreateDTO.getKopisTheatreId())
                .runtime(musicalCreateDTO.getRuntime())
                .ageLimit(musicalCreateDTO.getAgeLimit())
                .actorPreview(musicalCreateDTO.getActorPreview())
                .priceInfo(musicalCreateDTO.getPriceInfo())
                .description(musicalCreateDTO.getDescription())
                .posterUrl(musicalCreateDTO.getPosterUrl())
                .desImgUrl(musicalCreateDTO.getDesImgUrl())
                .build();
    }

    //kopisResponseDTO 를 musicalCreateDTO로 변환하는 메서드
    public static MusicalRequestDTO.MusicalCreateDTO convertKopisDTOToMusicalCreateDTO(KopisMusicalResponseDTO.KopisMusicalDTO kopisMusicalDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate perFrom = LocalDate.parse(kopisMusicalDTO.getPrfpdfrom(), formatter);
        LocalDate perTo = LocalDate.parse(kopisMusicalDTO.getPrfpdto(), formatter);

        return MusicalRequestDTO.MusicalCreateDTO.builder()
                .kopisMusicalId(kopisMusicalDTO.getMt20id())
                .name(kopisMusicalDTO.getPrfnm())
                .perFrom(perFrom)
                .perTo(perTo)
                .perPattern(kopisMusicalDTO.getDtguidance())
                .place(kopisMusicalDTO.getFcltynm())
                .runtime(kopisMusicalDTO.getPrfruntime())
                .ageLimit(kopisMusicalDTO.getPrfage())
                .actorPreview(kopisMusicalDTO.getPrfcast())
                .priceInfo(kopisMusicalDTO.getPcseguidance())
                .description(kopisMusicalDTO.getSty())
                .posterUrl(kopisMusicalDTO.getPoster())
                .desImgUrl(kopisMusicalDTO.getStyurls().getStyurls())
                .kopisTheatreId(kopisMusicalDTO.getMt10id())
                .build();
    }

    public static MusicalResponseDTO.MusicalResultDTO toMusicalResultDTO(Musical musical, EventResponseDTO.EventResultListDTO eventResultListDTO) {
        return MusicalResponseDTO.MusicalResultDTO.builder()
                .id(musical.getId())
                .kopisMusicalId(musical.getKopisMusicalId())
                .posterUrl(musical.getPosterUrl())
                .name(musical.getName())
                .place(musical.getPlace())
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .perPattern(musical.getPerPattern())
                .runTime(musical.getRuntime())
                .ageLimit(musical.getAgeLimit())
                .actorPreview(musical.getActorPreview())
                .priceInfo(musical.getPriceInfo())
                .eventList(eventResultListDTO)
                .description(musical.getDescription())
                .desImgUrl(musical.getDesImgUrl())
                .build();
    }

}
