package muit.backend.dto.inquiryDTO;

import lombok.Builder;
import lombok.Getter;

public class InquiryRequestDTO {


    @Builder
    @Getter
    public static class InquiryCreateRequestDTO {
        private String content;
        private String title;
    }

    @Getter
    public static class InquiryResponseRequestDTO {
        private String content;
    }

}
