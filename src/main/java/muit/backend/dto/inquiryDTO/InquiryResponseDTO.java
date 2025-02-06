package muit.backend.dto.inquiryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.dto.postDTO.LostResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralInquiryResultDTO {
        private int id;
        private String title;
        private String content;
        private Long MemberId;
        private LocalDateTime createdAt;
        private String status;
        private List<GeneralInquiryResponseResultDTO> responses;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralInquiryResponseResultDTO {
        private Long id;
        private String content;
        private Long MemberId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryResultListDTO{
        private List<InquiryResponseDTO.GeneralInquiryResultDTO> inquiries;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
