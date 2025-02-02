package muit.backend.dto.adminDTO.manageInquiryDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.InquiryStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ManageInquiryResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ManageInquiryResultListDTO { // 문의 관리 내역 전체 조회
        private Long inquiryId; // 문의 id
        private Long memberId; // 등록자 id
        private String userName; // 문의 등록자 아이디
        private String memberName; // 문의 등록자명
        private String email; // 등록자 이메일
        private String phone; // 등록자 전번
        private LocalDateTime createdAt;
        private InquiryStatus inquiryStatus; // 진행도 (미완료, 완료)
    }

    // === 특정 문의글 조회 ===
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageInquiryResultDTO {
        private Member member;
        private Inquiry inquiry;
        private Response response;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {
        private Long memberId;
        private String memberName;
        private String email;
        private String phone;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Inquiry {
        private Long inquiryId;
        private LocalDateTime createdAt;
        private InquiryStatus status;
        private String title;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long responseId;
        private String content;
        private LocalDateTime createdAt;
    }
}
