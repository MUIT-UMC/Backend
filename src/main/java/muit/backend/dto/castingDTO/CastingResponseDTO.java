package muit.backend.dto.castingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class CastingResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CastingResultListDTO {
        private Long musicalId;
        private String roleName;
        private List<ActorResultDTO> actorList;

    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActorResultDTO {
        private String realName;
        private String actorPic;
    }
}
