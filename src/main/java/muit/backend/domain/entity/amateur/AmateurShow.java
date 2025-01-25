package muit.backend.domain.entity.amateur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.AmateurStatus;
import muit.backend.dto.amateurTicketDTO.AmateurTicketRequestDTO;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmateurShow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    private String place;

    private String schedule;

    private String runtime;

    private String age;

    private String starring; // 출연자 목록, AmateurCasting 과는 다른기능입니다

    private Integer totalTicket;

    private Integer soldTicket;

    private String timeInfo;

    private String staff;

    private String account;

    private String hashtag;

    private String contact;

    private String rejectReason;

    private Integer cancelFee;

    @Enumerated(EnumType.STRING)
    private AmateurStatus amateurStatus = AmateurStatus.YET;


    //매핑 제거
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "schedule_id")
//    private Schedule schedule;

    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurTicket> amateurTicketList = new ArrayList<>();

    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurCasting> amateurCastingList = new ArrayList<>();

    @OneToMany(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private List<AmateurNotice> amateurNoticeList = new ArrayList<>();

    @OneToOne(mappedBy = "amateurShow", cascade = CascadeType.ALL)
    private AmateurSummary amateurSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //매핑 제거 -> ticket 엔티티 제거
//    @OneToMany(mappedBy = "coProduct", cascade = CascadeType.ALL)
//    private List<Ticket> ticketList = new ArrayList<>();

    public void updateAmateurShow(ManageAmateurShowRequestDTO.UpdateDTO requestDTO) {
        if(requestDTO.getSchedule()!=null){this.schedule = requestDTO.getSchedule();}
        if(requestDTO.getHashtag()!=null){this.hashtag = requestDTO.getHashtag();}
        if(requestDTO.getContent()!=null && this.amateurSummary!=null){this.amateurSummary.updateContent(requestDTO.getContent());}
        if(requestDTO.getContact()!=null){this.contact = requestDTO.getContact();}
        if(requestDTO.getAmateurStatus()!=null){this.amateurStatus = requestDTO.getAmateurStatus();}
    }

    public void decideAmateurShow(AmateurStatus amateurStatus, ManageAmateurShowRequestDTO.DecideDTO requestDTO) {

        // 상태 업데이트 (등록/반려)
        this.amateurStatus = amateurStatus;

        // AGAIN일 때만 반려 사유 설정 (필수는 아님)
        if (amateurStatus == AmateurStatus.AGAIN) {
            this.rejectReason = requestDTO.getRejectReason();
        } else if (amateurStatus == AmateurStatus.YET || amateurStatus == AmateurStatus.APPROVED) {
            this.rejectReason = null;  // APPROVED일 때는 명시적으로 null 설정
        }

    }

    public void updateAmateurTicket(AmateurTicketRequestDTO.AmateurTicketUpdateDTO requestDTO) {
        if(requestDTO.getName()!=null){this.name = requestDTO.getName();}
        if(requestDTO.getSchedule()!=null){this.schedule = requestDTO.getSchedule();}
        if(requestDTO.getSoldTicket()!=null){this.soldTicket = requestDTO.getSoldTicket();}
        if(requestDTO.getTotalTicket()!=null){this.totalTicket = requestDTO.getTotalTicket();}
    }

}
