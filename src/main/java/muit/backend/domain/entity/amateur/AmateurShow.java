package muit.backend.domain.entity.amateur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.common.BaseEntity;
import muit.backend.domain.entity.member.Member;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //매핑 제거 -> ticket 엔티티 제거
//    @OneToMany(mappedBy = "coProduct", cascade = CascadeType.ALL)
//    private List<Ticket> ticketList = new ArrayList<>();

}
