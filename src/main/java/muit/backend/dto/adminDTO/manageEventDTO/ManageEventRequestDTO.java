package muit.backend.dto.adminDTO.manageEventDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class ManageEventRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManageEventAddDTO {
        private LocalDate evFrom; // 이벤트 시작 날짜
        private LocalDate evTo; // 이벤트 종료 날짜
        private String name; // 이벤트 이름
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManageEventCreateDTO {
        private String musicalName; // 뮤지컬 이름
        private LocalDate evFrom; // 이벤트 시작 날짜
        private LocalDate evTo; // 이벤트 종료 날짜
        private String eventName; // 이벤트 이름
    }
}