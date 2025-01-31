package muit.backend.repository.amateurRepository;

import muit.backend.domain.entity.amateur.AmateurStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmateurStaffRepository extends JpaRepository<AmateurStaff, Long> {
}
