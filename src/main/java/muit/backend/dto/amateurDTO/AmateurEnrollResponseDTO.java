package muit.backend.dto.amateurDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class AmateurEnrollResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class EnrollResponseDTO{
        private Long id;
        private String name;
    }
}
