package muit.backend.dto.sectionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.SectionType;

import java.util.List;

public class SectionRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectionCreateDTO{
        private SectionType sectionType;
        private String floor;
        private String seatRange;
        private String viewDetail;
    }
}
