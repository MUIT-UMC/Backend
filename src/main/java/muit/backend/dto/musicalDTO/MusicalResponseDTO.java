package muit.backend.dto.musicalDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.entity.musical.Casting;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.EventType;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MusicalResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalResultDTO{

        private String bgImg;
        private String fancyTitle;
        private List<String> category;
        private String storyDescription;

        private Long id;
        private String name;
        private String posterUrl;
        private String place;
        private LocalDate perFrom;
        private LocalDate perTo;
        private String runTime;
        private String ageLimit;
        private List<String> actorPreview;
        private List<String> priceInfo;

        private EventResponseDTO.EventResultListDTO eventList;

        private String perPattern;

        private List<String> desImgUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalHomeDTO{
        private Long id;
        private String posterUrl;
        private String name;
        private String place;
        private String duration;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalHomeListDTO{
        private String message;
        private List<MusicalHomeDTO> musicalHomeList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalOpenDTO{
        private Long id;
        private String posterUrl;
        private String name;
        private String place;
        private String openDate;
        private String openInfo;
        private String dDay;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminMusicalDTO{
        private Long id;
        private String name;
        private String duration;
        private List<String> priceInfo;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminMusicalDetailDTO{
        private Long id;
        private String kopisMusicalId;
        private String name;
        private LocalDate perFrom;
        private LocalDate perTo;
        private String perPattern;
        private String theatreName;
        private String place;
        private String runtime;
        private String ageLimit;
        private List<String> actorPreview;
        private List<String> priceInfo;
        private EventResponseDTO.EventResultListDTO eventList;
        //직접 작성 - 배경이미지
        private String bgImg;
        //직접 작성 - 영문명
        private String fancyTitle;
        //직접 작성 - 태그
        private List<String> category;
        //직접 작성
        private LocalDateTime openDate;
        private EventType openInfo;
        //직접 작성 - 줄거리
        private String description;
        //직접 작성
//        private List<CastingResultDTO> castingList;
    }

}
