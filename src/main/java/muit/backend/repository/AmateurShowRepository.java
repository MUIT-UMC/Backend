package muit.backend.repository;

import muit.backend.domain.entity.amateur.AmateurShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmateurShowRepository extends JpaRepository<AmateurShow, Long> {

    // 전체 조회용 (member 함께 조회)
    @Query("SELECT a FROM AmateurShow a JOIN FETCH a.member")
    Page<AmateurShow> findAllWithMember(Pageable pageable);

    // 검색어가 있을 때
    @Query("SELECT a FROM AmateurShow a JOIN FETCH a.member WHERE " +
            "a.name LIKE %:keyword% " +
            "OR a.schedule LIKE %:keyword% " +
            "OR a.member.name LIKE %:keyword%")
    Page<AmateurShow> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

    // 특정 공연 조회용 (member와 summary 함께 조회)
    @Query("SELECT a FROM AmateurShow a " +
            "JOIN FETCH a.member " +
            "LEFT JOIN FETCH a.amateurSummary " +
            "WHERE a.id = :amateurShowId")
    Optional<AmateurShow> findByIdWithMemberAndSummary(@Param("amateurShowId") Long amateurShowId);

    // 티켓 검색 조회
    @Query("SELECT a FROM AmateurShow a WHERE " +
            "a.name LIKE %:keyword% " +
            "OR a.schedule LIKE %:keyword%")
    Page<AmateurShow> findTicketsByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @Query("SELECT a FROM AmateurShow a ORDER BY a.schedule DESC")
    Page<AmateurShow> findAllShows(Pageable pageable);
}
