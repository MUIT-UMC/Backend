package muit.backend.repository;

import muit.backend.domain.entity.musical.Musical;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, Long> {
    List<Musical> findTop5ByOrderByIdAsc();
    List<Musical> findAllByOrderByIdAsc(Pageable pageable);

    @Query(value = "SELECT * FROM musical m WHERE m.open_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY) ORDER BY m.open_date ASC", nativeQuery = true)
    List<Musical> getFiveOpenWithin7Days(Pageable pageable);

    @Query("SELECT m FROM Musical m WHERE m.openDate > CURRENT_TIMESTAMP ORDER BY m.openDate ASC")
    List<Musical> getAllOpenAfterToday(Pageable pageable);

    List<Musical> findByNameContaining(String name);
}
