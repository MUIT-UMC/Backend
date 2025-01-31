package muit.backend.repository.amateurRepository;

import muit.backend.domain.entity.amateur.AmateurNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmateurNoticeRepository extends JpaRepository<AmateurNotice, Long> {
}
