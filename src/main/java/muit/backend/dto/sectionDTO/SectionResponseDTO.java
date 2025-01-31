package muit.backend.dto.sectionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.SectionType;

import java.util.List;

public class SectionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectionResultDTO{
        private String allSeatImg;
        private Long theatreId;
        private String theatreName;
        private String address;
        private Long musicalId;
        private String musicalName;
        private SectionType sectionType;
        private String floor;
        private String seatRange;
        private String viewPic;
        private String viewDetail;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FloorResultDTO{
        private String allSeatImg;
        private Long theatreId;
        private String theatreName;
        private String address;
        private Long musicalId;
        private String musicalName;

        private List<SectionType> sectionTypes;
    }

}
