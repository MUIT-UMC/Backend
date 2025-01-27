package muit.backend.dto.manageReservDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.ReservationStatus;

public class ManageReservResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가진 필드는 JSON 응답에서 제외
    public static class ManageReservResultListDTO { // 소극장 공연 관리 내역 전체 조회
        private Long reservationId; // 소극장 예약 id
        private String memberName; // 예약자(사용자)명
        private String amateurShowName; // 소극장 공연명
        private String schedule; // 소극장 공연 날짜/시간
        private String place; // 소극장 공연 장소
        private Integer quantity;
        private ReservationStatus reservationStatus; // 예약 상태 (예약 중, 예약 완료, 사용 완료, 취소 중, 취소 완료)
    }
}
