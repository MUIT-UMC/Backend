package muit.backend.domain.entity.member;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.ReportObjectType;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long reportedObjectId;

    @Enumerated(EnumType.STRING)
    private ReportObjectType reportObjectType;


}
