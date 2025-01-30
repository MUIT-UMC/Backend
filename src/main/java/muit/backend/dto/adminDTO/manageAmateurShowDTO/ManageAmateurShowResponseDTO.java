package muit.backend.dto.adminDTO.manageAmateurShowDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.AmateurStatus;

public class ManageAmateurShowResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가진 필드는 JSON 응답에서 제외
    public static class ManageAmateurShowResultListDTO { // 소극장 공연 관리 내역 전체 조회
        private Long amateurShowId; // 소극장 공연 id
        private String amateurShowName; // 소극장 공연명
        private String schedule; // 소극장 공연 날짜/시간
        private String memberName; // 등록자(사용자)명
        private AmateurStatus amateurStatus; // 등록 상태 (확인 전, 등록, 반려)
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageAmateurShowResultDTO { // 특정 소극장 공연 조회, 특정 소극장 공연 수정

        private Long amateurShowId; // 소극장 공연 id
        private String amateurShowName; // 소극장 공연명
        private String memberName; // 등록자(사용자)명
        private String username; // 등록자 아이디
        private String schedule; // 소극장 공연 날짜/시간
        private String hashtag; // 해시태그
        private String content; // 공연 줄거리
        private String account; // 계좌번호
        private String contact; // 연락처
        private AmateurStatus amateurStatus; // 등록 상태 (확인 전, 등록, 반려)
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageAmateurShowDecideDTO { // 소극장 공연 등록/반려

        private Long amateurShowId; // 소극장 공연 id
        private String amateurShowName; // 소극장 공연명
        private String memberName; // 등록자(사용자)명
        private String username; // 등록자 아이디
        private AmateurStatus amateurStatus; // 등록 상태 (확인 전, 등록, 반려)
        private String rejectReason; // 반려 사유
    }
}
