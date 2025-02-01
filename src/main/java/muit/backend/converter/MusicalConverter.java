package muit.backend.converter;

import muit.backend.domain.entity.musical.Casting;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.EventType;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
                .bgImg(musical.getBgImg())
                .fancyTitle(musical.getFancyTitle())
                .category(musical.getCategory())
                .storyDescription(musical.getDescription())
                .id(musical.getId())
                .name(musical.getName())
                .posterUrl(musical.getPosterUrl())
                .place(musical.getPlace())
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .runTime(musical.getRuntime())
                .ageLimit(musical.getAgeLimit())
                .actorPreview(musical.getActorPreview())
                .priceInfo(musical.getPriceInfo())
                .eventList(eventResultListDTO)
                .perPattern(musical.getPerPattern())
                .desImgUrl(musical.getDesImgUrl())
                .build();
    }

    public static MusicalResponseDTO.MusicalHomeDTO toMusicalHomeDTO(Musical musical) {
        return MusicalResponseDTO.MusicalHomeDTO.builder()
                .id(musical.getId())
                .name(musical.getName())
                .place(musical.getPlace())
                .posterUrl(musical.getPosterUrl())
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .build();
    }

    public static MusicalResponseDTO.MusicalHomeListDTO toMusicalHomeListDTO(List<Musical> musicals) {
        List<MusicalResponseDTO.MusicalHomeDTO> musicalHomeList = musicals.stream()
                .map(MusicalConverter::toMusicalHomeDTO).collect(Collectors.toList());
        String msg = "검색 결과";
        if(musicals.isEmpty()) msg= "검색 결과가 존재하지 않습니다.";
        return MusicalResponseDTO.MusicalHomeListDTO.builder()
                .message(msg)
                .musicalHomeList(musicalHomeList)
                .build();
    }

    public static MusicalResponseDTO.MusicalOpenDTO toMusicalOpenDTO(Musical musical) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M.dd (E) HH:mm", Locale.KOREA);
        String openDate = musical.getOpenDate().format(formatter);
        EventType s = musical.getOpenInfo();
        String openInfo;
        switch(s){
            case FIRST:
                openInfo = "1차 오픈";
                break;
            case SECOND:
                openInfo = "2차 오픈";
                break;
            case THIRD:
                openInfo = "3차 오픈";
                break;
            default:
                openInfo = "일반 예매";
        }

        return MusicalResponseDTO.MusicalOpenDTO.builder()
                .id(musical.getId())
                .name(musical.getName())
                .place(musical.getPlace())
                .posterUrl(musical.getPosterUrl())
                .openDate(openDate)
                .openInfo(openInfo)
                .build();
    }

    public static MusicalResponseDTO.MusicalOpenListDTO toMusicalOpenListDTO(List<Musical> musicals) {
        List<MusicalResponseDTO.MusicalOpenDTO> musicalOpenList = musicals.stream()
                .map(MusicalConverter::toMusicalOpenDTO).collect(Collectors.toList());

        return MusicalResponseDTO.MusicalOpenListDTO.builder()
                .musicalOpenList(musicalOpenList)
                .build();
    }

    public static MusicalResponseDTO.AdminMusicalDTO toAdminMusicalDTO(Musical musical) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d", Locale.KOREA);

        String duration = musical.getPerFrom().format(formatter) + " ~ " + musical.getPerTo().format(formatter);

        return MusicalResponseDTO.AdminMusicalDTO.builder()
                .id(musical.getId())
                .name(musical.getName())
                .place(musical.getPlace())
                .duration(duration)
                .build();
    }

    public static MusicalResponseDTO.AdminMusicalDetailDTO adminMusicalDetailDTO(Musical musical, EventResponseDTO.EventResultListDTO eventList) {


        return MusicalResponseDTO.AdminMusicalDetailDTO.builder()
                .id(musical.getId())
                .kopisMusicalId(musical.getKopisMusicalId())
                .name(musical.getName())
                .place(musical.getPlace())
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .perPattern(musical.getPerPattern())
                .theatreName(musical.getTheatre().getName())
                .place(musical.getPlace())
                .runtime(musical.getRuntime())
                .ageLimit(musical.getAgeLimit())
                .actorPreview(musical.getActorPreview())
                .priceInfo(musical.getPriceInfo())
                .eventList(eventList)
                .bgImg(musical.getBgImg())
                .description(musical.getDescription())
                .fancyTitle(musical.getFancyTitle())
                .category(musical.getCategory())
                .openDate(musical.getOpenDate())
                .openInfo(musical.getOpenInfo())
                .build();
    }
}
