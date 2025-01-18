package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.InquiryStatus;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'AWAIT'")
    private InquiryStatus inquiryStatus;


    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private List<InquiryResponse> inquiryResponseList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
