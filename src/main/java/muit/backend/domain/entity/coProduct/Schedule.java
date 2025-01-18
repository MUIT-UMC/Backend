package muit.backend.domain.entity.coProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.enums.OpenStatus;

import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date perDate;

    private String perDay;

    private String perTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "musical_id")
    private Musical musical;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private CoProduct coProduct;
}
