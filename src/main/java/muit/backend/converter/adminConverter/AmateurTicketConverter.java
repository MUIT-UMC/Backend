package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.dto.adminDTO.amateurTicketDTO.AmateurTicketResponseDTO;

import java.util.Set;

public class AmateurTicketConverter {

    public static AmateurTicketResponseDTO.AmateurTicketResultListDTO toAmateurTicketResultListDTO(AmateurShow amateurShow, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            return AmateurTicketResponseDTO.AmateurTicketResultListDTO.builder()
                    .amateurShowId(amateurShow.getId())
                    .name(amateurShow.getName())
                    .schedule(amateurShow.getSchedule())
                    .soldTicket(amateurShow.getSoldTicket())
                    .totalTicket(amateurShow.getTotalTicket())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return AmateurTicketResponseDTO.AmateurTicketResultListDTO.builder()
                .amateurShowId(amateurShow.getId())  // ID는 항상 포함
                .name(selectedFields.contains("name") ? amateurShow.getName() : null)
                .schedule(selectedFields.contains("schedule") ? amateurShow.getSchedule() : null)
                .soldTicket(selectedFields.contains("reservationStatus") ? amateurShow.getSoldTicket() : null)
                .totalTicket(selectedFields.contains("reservationStatus") ? amateurShow.getTotalTicket() : null)
                .build();
    }

    public static AmateurTicketResponseDTO.AmateurTicketResultDTO toAmateurTicketResultDTO(AmateurShow amateurShow) {
        return AmateurTicketResponseDTO.AmateurTicketResultDTO.builder()
                .amateurShowId(amateurShow.getId())
                .name(amateurShow.getName())
                .schedule(amateurShow.getSchedule())
                .soldTicket(amateurShow.getSoldTicket())
                .totalTicket(amateurShow.getTotalTicket())
                .build();
    }
}
