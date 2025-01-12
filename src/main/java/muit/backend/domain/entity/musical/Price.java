package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;


@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer RPrice;

    private Integer SPrice;

    private Integer APrice;

    private Integer BPrice;

    private Integer VIPPrice;

    private Integer RDiscount;

    private Integer SDiscount;

    private Integer ADiscount;

    private Integer BDiscount;

    private Integer VIPDiscount;

    @OneToOne(mappedBy = "price", cascade = CascadeType.ALL)
    private Musical musical;

}
