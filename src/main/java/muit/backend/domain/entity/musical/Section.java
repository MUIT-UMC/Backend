package muit.backend.domain.entity.musical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.enums.EventType;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.sectionDTO.SectionRequestDTO;

import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Section extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    private String seatRange;

    private String viewPic;

    private String viewDetail;

    private String floor;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    public Section updateViewPic(String imgUrl){
        this.viewPic = imgUrl;
        return this;
    }

    public Section changeSection(SectionRequestDTO.SectionCreateDTO sectionCreateDTO){
        if(sectionCreateDTO.getSeatRange() != null){
            this.seatRange = sectionCreateDTO.getSeatRange();
        }
        if (sectionCreateDTO.getSectionType() != null){
            this.sectionType = sectionCreateDTO.getSectionType();
        }
        if(sectionCreateDTO.getViewDetail() != null){
            this.viewDetail = sectionCreateDTO.getViewDetail();
        }
        if(sectionCreateDTO.getFloor() != null){
            this.floor = sectionCreateDTO.getFloor();
        }
        return this;
    }

}
