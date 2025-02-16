package muit.backend.dto.eventDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EventResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventResultDTO {
        private Long id;
        private Long musicalId;
        private String name;
        private String duration;
        private LocalDate evFrom;
        private LocalDate evTo;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventResultListDTO {
        private Long musicalId;
        private String posterUrl;
        private String musicalName;
        private String place;
        private String duration;
        private LocalDate perFrom;
        private LocalDate perTo;
        private Boolean isLike;

        private List<EventResultDTO> eventResultListDTO;

    }



}
