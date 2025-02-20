package muit.backend.repository;

import muit.backend.domain.entity.musical.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByMusicalIdOrderByEvFromAsc(long musicalId);

    List<Event> findAllByEvFromIsNotNullOrderByEvToAsc();
}
