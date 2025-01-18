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
public class Section extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatRange;

    private String viewPic;

    private String viewDetail;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;


}
