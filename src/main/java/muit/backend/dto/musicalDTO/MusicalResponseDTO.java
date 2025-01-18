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
import java.util.List;


public class MusicalResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicalResultDTO{
        private Long id;
        private String desImgUrl;
        private String posterUrl;
        private String musicalName;
        private String theatreName;
        private String perPattern;
        private String runTime;
        private String ageLimit;
        private List<String> actorList;
        private String discountInfo;

        private List<String> priceInfo;

        private EventResponseDTO.EventResultListDTO eventList;

        private String description;
        private String desImg2Url;

    }
}
