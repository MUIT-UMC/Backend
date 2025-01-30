package muit.backend.repository;

import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 검색어가 있을 때
    @Query("SELECT m FROM Musical m WHERE " +
            "m.name LIKE %:keyword% " +
            "OR m.place LIKE %:keyword%")
    Page<Musical> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

    // 이벤트가 있는 뮤지컬 전체 조회
    @Query("SELECT DISTINCT m FROM Musical m JOIN m.eventList e WHERE e IS NOT NULL")
    Page<Musical> findAllWithEvents(Pageable pageable);

    Optional<Musical> findByName(String name);
}
