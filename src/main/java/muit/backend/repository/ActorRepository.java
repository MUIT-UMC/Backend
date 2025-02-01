package muit.backend.repository;

import muit.backend.domain.entity.musical.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findAllByCastingId(Long castingId);
}
