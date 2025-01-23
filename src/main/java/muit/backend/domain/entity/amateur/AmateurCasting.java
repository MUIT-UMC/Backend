package muit.backend.domain.entity.amateur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmateurCasting extends BaseEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String actorCasting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amateur_show_id")
    private AmateurShow amateurShow;

}
