package muit.backend.dto.adminDTO.amateurTicketDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.Gender;

public class AmateurTicketResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가진 필드는 JSON 응답에서 제외
    public static class AmateurTicketResultListDTO { // 티켓 전체 조회
        private Long amateurShowId; // 소극장 공연 아이디
        private String name; // 소극장 공연 이름
        private String schedule; // 공연 날짜/시간
        private Integer soldTicket; // 예매된 티켓 수
        private Integer totalTicket; // 전체 티켓 수
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AmateurTicketResultDTO { // 특정 티켓 조회, 수정
        private Long amateurShowId; // 소극장 공연 아이디
        private String name; // 소극장 공연 이름
        private String schedule; // 공연 날짜/시간
        private Integer soldTicket; // 예매된 티켓 수
        private Integer totalTicket; // 전체 티켓 수
    }
}
