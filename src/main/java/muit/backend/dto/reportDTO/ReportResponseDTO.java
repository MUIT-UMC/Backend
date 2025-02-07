package muit.backend.dto.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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
        private Long postId;
        private LocalDateTime createdAt;
        private Long memberId;
    }
}
