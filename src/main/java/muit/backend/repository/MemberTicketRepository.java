package muit.backend.repository;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberTicketRepository extends JpaRepository<MemberTicket, Long> {
    List<MemberTicket> findByMember(Member member);

    List<MemberTicket> findByMemberAndReservationStatus(Member member, ReservationStatus status);

    // 어드민 예약 전체 조회용
    @Query("SELECT mt FROM MemberTicket mt " +
            "JOIN FETCH mt.member " +
            "JOIN FETCH mt.amateurTicket aticket " +
            "JOIN FETCH aticket.amateurShow ashow")
    Page<MemberTicket> findAllMemberTickets(Pageable pageable);

    // 검색어가 있을 때
    @Query("SELECT mt FROM MemberTicket mt " +
            "JOIN FETCH mt.member m " +
            "JOIN FETCH mt.amateurTicket aticket " +
            "JOIN FETCH aticket.amateurShow ashow " +
            "WHERE m.name LIKE %:keyword% " +
            "   OR ashow.name LIKE %:keyword% " +
            "   OR ashow.schedule LIKE %:keyword% " +
            "   OR ashow.place LIKE %:keyword%")
    Page<MemberTicket> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

}
