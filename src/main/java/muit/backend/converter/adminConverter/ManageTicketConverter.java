package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.dto.adminDTO.manageTicketDTO.ManageTicketResponseDTO;

import java.util.Set;

public class ManageTicketConverter {

    public static ManageTicketResponseDTO.ManageTicketResultListDTO toManageTicketResultListDTO(MemberTicket memberTicket, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {

            return ManageTicketResponseDTO.ManageTicketResultListDTO.builder()
                    .memberTicketId(memberTicket.getId())
                    .memberName(memberTicket.getMember().getName())
                    .amateurShowName(memberTicket.getAmateurTicket().getAmateurShow().getName())
                    .schedule(memberTicket.getAmateurTicket().getAmateurShow().getSchedule())
                    .place(memberTicket.getAmateurTicket().getAmateurShow().getPlace())
                    .quantity(memberTicket.getQuantity())
                    .reservationStatus(memberTicket.getReservationStatus())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return ManageTicketResponseDTO.ManageTicketResultListDTO.builder()
                .memberTicketId(memberTicket.getId())  // ID는 항상 포함
                .amateurShowName(selectedFields.contains("amateurShowName") ? memberTicket.getAmateurTicket().getAmateurShow().getName() : null)
                .schedule(selectedFields.contains("schedule") ? memberTicket.getAmateurTicket().getAmateurShow().getSchedule() : null)
                .reservationStatus(selectedFields.contains("reservationStatus") ? memberTicket.getReservationStatus() : null)
                .build();
    }

    public static ManageTicketResponseDTO.ManageTicketResultDTO toManageTicketResultDTO(MemberTicket memberTicket) {
        return ManageTicketResponseDTO.ManageTicketResultDTO.builder()
                .memberTicketId(memberTicket.getId())
                .memberName(memberTicket.getMember().getName())
                .amateurShowName(memberTicket.getAmateurTicket().getAmateurShow().getName())
                .schedule(memberTicket.getAmateurTicket().getAmateurShow().getSchedule())
                .place(memberTicket.getAmateurTicket().getAmateurShow().getPlace())
                .quantity(memberTicket.getQuantity())
                .reservationStatus(memberTicket.getReservationStatus())
                .build();
    }

}
