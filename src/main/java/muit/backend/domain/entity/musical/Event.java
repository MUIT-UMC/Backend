package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.EventType;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate evFrom;

    private LocalDate evTo;

    private String place;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "musical_id")
    private Musical musical;

}
