package muit.backend.domain.entity.coProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatFloor;

    private String seatColumn;

    private Integer seatRow;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coProduct_id")
    private CoProduct coProduct;
}
