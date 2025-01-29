package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.entity.member.Reservation;
import muit.backend.dto.adminDTO.manageReservDTO.ManageReservResponseDTO;

import java.util.Set;

public class ManageReservConverter {

    // reservation에서 amateurshow 가져오기
    private static AmateurShow getAmateurShowFromReservation(Reservation reservation) {
        return reservation.getMemberTicketList().stream()
                .findFirst() // 아무 티켓이나 봐도 같은 공연이니 첫 번째 것 사용
                .map(memberTicket -> memberTicket.getAmateurTicket().getAmateurShow())
                .orElse(null);
    }

    public static ManageReservResponseDTO.ManageReservResultListDTO toManageReservResultListDTO(Reservation reservation, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            AmateurShow amateurShow = getAmateurShowFromReservation(reservation);

            return ManageReservResponseDTO.ManageReservResultListDTO.builder()
                    .reservationId(reservation.getId())
                    .memberName(reservation.getMember().getName())
                    .amateurShowName(amateurShow.getName())
                    .schedule(amateurShow.getSchedule())
                    .place(amateurShow.getPlace())
                    .quantity(reservation.getMemberTicketList().stream()
                            .findFirst()
                            .map(MemberTicket::getQuantity)
                            .orElse(null))
                    .reservationStatus(reservation.getStatus())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        AmateurShow amateurShow = getAmateurShowFromReservation(reservation);
        return ManageReservResponseDTO.ManageReservResultListDTO.builder()
                .reservationId(reservation.getId())  // ID는 항상 포함
                .amateurShowName(selectedFields.contains("amateurShowName") ?
                        (amateurShow != null ? amateurShow.getName() : null) : null)
                .schedule(selectedFields.contains("schedule") ?
                        (amateurShow != null ? amateurShow.getSchedule() : null) : null)
                .reservationStatus(selectedFields.contains("reservationStatus") ?
                        reservation.getStatus() : null)
                .build();
    }

}
