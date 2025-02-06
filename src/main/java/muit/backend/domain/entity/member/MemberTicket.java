package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.amateur.AmateurTicket;
import muit.backend.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTicket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Integer totalPrice;

    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amateur_ticket_id")
    private AmateurTicket amateurTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void cancelTicket(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void updateMemberTicket(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
