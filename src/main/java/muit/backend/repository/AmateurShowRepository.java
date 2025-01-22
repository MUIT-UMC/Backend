package muit.backend.repository;

import muit.backend.domain.entity.amateur.AmateurShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AmateurShowRepository extends JpaRepository<AmateurShow, Long> {

    // 검색어가 있을 때
    @Query("SELECT a FROM AmateurShow a JOIN FETCH a.member WHERE " +
            "a.name LIKE %:keyword% " +
            "OR a.schedule LIKE %:keyword% " +
            "OR a.member.name LIKE %:keyword%")
    Page<AmateurShow> findByKeyword(Pageable pageable, @Param("keyword") String keyword);
}
