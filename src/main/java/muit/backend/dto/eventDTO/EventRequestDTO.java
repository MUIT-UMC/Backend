package muit.backend.dto.eventDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class EventRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventCreateDTO {
        private LocalDate evFrom;
        private LocalDate evTo;
        private String eventName;
    }
}
