package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.amateur.AmateurSummary;
import muit.backend.domain.enums.InquiryStatus;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'AWAIT'")
    private InquiryStatus inquiryStatus;

    @OneToOne(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private InquiryResponse inquiryResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 답변 추가되면 상태 변경
    public void addResponse(InquiryResponse response) {
        this.inquiryStatus = InquiryStatus.COMPLETED;
    }
}
