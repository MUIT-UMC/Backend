package muit.backend.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.domain.enums.TicketType;

import java.time.LocalDateTime;
import java.util.List;


public class TicketResponseDTO {


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberTicketResponseDTO{
        private Long memberTicketId;
        private Long memberId;
        private Long amateurTicketId;
        private int quantity;
        private int totalPrice;
        private LocalDateTime date;
        private String accountName;
        private ReservationStatus reservationStatus;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AmateurShowForTicketDTO {
        private String amateurShowId;
        private String posterImgUrl;
        private String name;
        private String place;
        private String schedule;
        private List<SelectionTicketInfoDTO> tickets;
        private ReserveConfirmMemberDTO reserveConfirmMemberDTO;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectionTicketInfoDTO {
        private String amateurTicketId;
        private TicketType ticketType;
        private String ticketName;
        private Integer price;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReserveConfirmMemberDTO {
        private String memberId;
        private String memberName;
        private String memberEmail;
        private String memberPhone;
        private String memberBirth;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPageTicketDTO {
        private Long memberTicketId;
        private String posterImgUrl;
        private String amateurShowName;
        private Integer quantity;
        private ReservationStatus reservationStatus;
        private LocalDateTime reservationDate;
        private String place;
        private String cancelDate;
        private String schedule;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyPageTicketListDTO {
        private List<MyPageTicketDTO> tickets;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CancelRequestTicketDTO {
        private Long memberTicketId;
        private ReservationStatus reservationStatus;
    }

}
