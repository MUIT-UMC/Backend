package muit.backend.repository;

import muit.backend.domain.entity.musical.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByName(String name);

    Optional<Theatre> findAllById(Long theatreId);
}
