package muit.backend.dto.adminDTO.manageEventDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.entity.musical.Event;

import java.time.LocalDate;
import java.util.List;

public class ManageEventResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가진 필드는 JSON 응답에서 제외
    public static class ManageEventResultListDTO {
        private Long musicalId; // 뮤지컬 아이디
        private String name; // 뮤지컬 이름
        private String place; // 뮤지컬 장소
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageEventResultDTO {
        private Long musicalId; // 뮤지컬 아이디
        private String musicalName; // 뮤지컬 이름
        private String place; // 뮤지컬 장소
        private List<EventInfo> events; // 여러 개의 이벤트 정보를 담을 리스트
    }

    @Getter
    @AllArgsConstructor
    public static class EventInfo {
        private LocalDate evFrom;
        private LocalDate evTo;
        private String eventName;
    }
}
