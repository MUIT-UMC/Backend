package muit.backend.repository;

import muit.backend.domain.entity.musical.Musical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, Long> {
}
