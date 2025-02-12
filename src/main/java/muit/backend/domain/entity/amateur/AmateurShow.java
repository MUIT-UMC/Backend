package muit.backend.domain.entity.amateur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.AmateurStatus;
import muit.backend.dto.adminDTO.amateurTicketDTO.AmateurTicketRequestDTO;
import muit.backend.dto.adminDTO.manageAmateurShowDTO.ManageAmateurShowRequestDTO;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmateurShow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String posterImgUrl;

    private String place;

    private String schedule;

    private String runtime;

    private String age;

    private String starring; // 출연자 목록, AmateurCasting 과는 다른기능입니다

    private Integer totalTicket;

    @ColumnDefault("0")
    private Integer soldTicket;

    private String timeInfo; // 공연시간 정보, runtime과 다름

    //private String staff; // 제거 예정,

    private String account;

    private String hashtag;

    private String contact;

    private String rejectReason;

    private Integer cancelFee;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AmateurStatus amateurStatus = AmateurStatus.YET;


    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurTicket> amateurTicketList = new ArrayList<>();

    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurCasting> amateurCastingList = new ArrayList<>();

    @OneToOne(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private AmateurNotice amateurNotice;

    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurStaff> amateurStaffList = new ArrayList<>();

    @OneToOne(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private AmateurSummary amateurSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAmateurShow(ManageAmateurShowRequestDTO.ManageAmateurShowUpdateDTO requestDTO) {
        if (requestDTO.getSchedule() != null) {
            this.schedule = requestDTO.getSchedule();
        }
        if (requestDTO.getHashtag() != null) {
            this.hashtag = requestDTO.getHashtag();
        }
        if (requestDTO.getContent() != null && this.amateurSummary != null) {
            this.amateurSummary.updateContent(requestDTO.getContent());
        }
        if (requestDTO.getContact() != null) {
            this.contact = requestDTO.getContact();
        }
        if (requestDTO.getAmateurStatus() != null) {
            this.amateurStatus = requestDTO.getAmateurStatus();
        }
    }

    public void updateAmateurTicket(AmateurTicketRequestDTO.AmateurTicketUpdateDTO requestDTO) {
        if (requestDTO.getName() != null) {
            this.name = requestDTO.getName();
        }
        if (requestDTO.getSchedule() != null) {
            this.schedule = requestDTO.getSchedule();
        }
        if (requestDTO.getSoldTicket() != null) {
            this.soldTicket = requestDTO.getSoldTicket();
        }
        if (requestDTO.getTotalTicket() != null) {
            this.totalTicket = requestDTO.getTotalTicket();
        }
    }

    public void updateRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void createRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void updateAmateurStatus(AmateurStatus amateurStatus) {
        this.amateurStatus = amateurStatus;
    }

    public void updateSoldTicket(Integer soldTicket) {
        this.soldTicket = soldTicket;
    }
}
