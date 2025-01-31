package muit.backend.repository;

import muit.backend.domain.entity.member.MemberTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTicketRepository extends JpaRepository<MemberTicket, Long> {
}
