package muit.backend.s3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UuidFileRepository extends JpaRepository<UuidFile, Long> {
}
