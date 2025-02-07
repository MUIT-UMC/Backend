package muit.backend.dto.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.ReportObjectType;

import java.time.LocalDateTime;
import java.util.List;


public class ReportResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportResultDTO {
        private Long id;
        private String message;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralReportDTO {
        private Long id;
        private String content;
        private Long reportedObjectId;
        private ReportObjectType objectType;
        private LocalDateTime createdAt;
        private Long memberId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralReportListDTO {
        private List<GeneralReportDTO> reports;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
