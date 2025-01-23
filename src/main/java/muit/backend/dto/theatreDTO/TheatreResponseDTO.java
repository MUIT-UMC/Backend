package muit.backend.dto.theatreDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.enums.PostType;
import muit.backend.domain.enums.SectionType;

import java.util.List;

public class TheatreResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheatreResultDTO{
        private Long id;
        private String theatreName;
        private String address;
        private String theatrePic;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheatreResultListDTO{
        private List<TheatreResultDTO> theatreResults;
    }
}
