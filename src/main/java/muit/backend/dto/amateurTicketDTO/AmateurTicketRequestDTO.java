package muit.backend.dto.amateurTicketDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AmateurTicketRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AmateurTicketUpdateDTO {
        private String name; // 소극장 공연 이름
        private String schedule; // 공연 날짜/시간
        private Integer soldTicket; // 예매된 티켓 수
        private Integer totalTicket; // 전체 티켓 수
    }
}
