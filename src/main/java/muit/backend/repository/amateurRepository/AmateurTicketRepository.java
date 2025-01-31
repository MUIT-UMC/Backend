package muit.backend.repository.amateurRepository;

import muit.backend.domain.entity.amateur.AmateurTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmateurTicketRepository extends JpaRepository<AmateurTicket, Long> {
}
