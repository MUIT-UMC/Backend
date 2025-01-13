package muit.backend.domain.entity.coProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.ReservationStatus;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer copy;

    private Integer payment;

    private Integer fee;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'RESERVE_AWAIT'")
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coProduct_id")
    private CoProduct coProduct;

}
