package muit.backend.dto.adminDTO.inquiryResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class InquiryResponseResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InquiryResponseUpsertDTO { // 답변 생성
        private Long inquiryId;
        private Long responseId;
        private String content;
        private LocalDateTime createdAt;
    }
}
