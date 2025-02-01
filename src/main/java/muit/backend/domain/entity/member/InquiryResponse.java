package muit.backend.domain.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    public void updateContent(String content) {
        this.content = content;
    }
}
