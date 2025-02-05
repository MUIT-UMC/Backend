package muit.backend.repository;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberTicketRepository extends JpaRepository<MemberTicket, Long> {
    List<MemberTicket> findByMember(Member member);

    List<MemberTicket> findByMemberAndReservationStatus(Member member, ReservationStatus status);

}
