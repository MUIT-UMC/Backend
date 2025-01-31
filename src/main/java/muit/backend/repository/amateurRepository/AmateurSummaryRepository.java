package muit.backend.repository.amateurRepository;

import muit.backend.domain.entity.amateur.AmateurSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmateurSummaryRepository extends JpaRepository<AmateurSummary, Long> {
}
