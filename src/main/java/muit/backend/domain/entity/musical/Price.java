package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;

import java.util.List;


@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String VIPPrice;
    private String VIPDiscount;

    private String RPrice;
    private String RDiscount;

    private String SPrice;
    private String SDiscount;

    private String APrice;
    private String ADiscount;

    private String BPrice;
    private String BDiscount;

    @OneToOne(mappedBy = "price", cascade = CascadeType.ALL)
    private Musical musical;

    public List<String> getPriceAsStringList() {
        return List.of(
                String.valueOf(VIPPrice),
                String.valueOf(VIPDiscount),
                String.valueOf(RPrice),
                String.valueOf(RDiscount),
                String.valueOf(SPrice),
                String.valueOf(SDiscount),
                String.valueOf(APrice),
                String.valueOf(ADiscount),
                String.valueOf(BPrice),
                String.valueOf(BDiscount)
        );
    }

}
