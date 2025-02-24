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
        private String message;
        private List<TheatreResultDTO> theatreResults;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminTheatreDetailDTO{
        private Long theatreId;
        private String theatreName;
        private String address;
        private Long musicalId;
        private String musicalName;
        private String theatrePic;
        private String allSeatImg;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminTheatreSectionDTO{
        private SectionType sectionType;
        private String floor;
        private String seatRange;
        private String viewDetail;
        private Boolean isViewPic;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminTheatreSectionListDTO{
        private Long theatreId;
        private String theatreName;
        private Boolean isTheatrePic;
        private List<AdminTheatreSectionDTO> theatreSections;
    }



}
