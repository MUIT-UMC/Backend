package muit.backend.domain.entity.amateur;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.member.Member;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmateurNotice extends BaseEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amateur_show_id")
    private AmateurShow amateurShow;
}
