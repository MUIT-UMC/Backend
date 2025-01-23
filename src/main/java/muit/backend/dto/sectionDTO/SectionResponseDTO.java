package muit.backend.dto.sectionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.SectionType;

public class SectionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectionResultDTO{

        private Long theatreId;
        private String theatreName;
        private String address;
        private Long musicalId;
        private String musicalName;
        private String allSeatImg;
        private String viewPic;
        private String viewDetail;
        private SectionType sectionType;
    }
}
