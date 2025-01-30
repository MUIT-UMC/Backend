package muit.backend.dto.musicalDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.io.Serializable;
import java.time.LocalDate;
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
        private LocalDate perFrom;
        private LocalDate perTo;
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
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalOpenListDTO{
        private List<MusicalOpenDTO> musicalOpenList;
    }

}
