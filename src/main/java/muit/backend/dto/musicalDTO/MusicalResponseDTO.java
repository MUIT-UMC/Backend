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

        private Long id;
        private String kopisMusicalId;
        private List<String> desImgUrl;
        private String posterUrl;
        private String name;
        private String place;
        private LocalDate perFrom;
        private LocalDate perTo;
        private String perPattern;
        private String runTime;
        private String ageLimit;
        private List<String> actorPreview;
        private List<String> priceInfo;

        private EventResponseDTO.EventResultListDTO eventList;

        //직접 작성
        private String description;
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
