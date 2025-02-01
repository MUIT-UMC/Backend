package muit.backend.repository;

import muit.backend.domain.entity.musical.Casting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CastingRepository extends JpaRepository<Casting, Long> {
    List<Casting> findByMusicalId(Long musicalId);
}
