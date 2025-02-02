package muit.backend.repository;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByNameContaining(String name);

    Page<Theatre> findAll(Pageable pageable);

    @Query("SELECT t FROM Theatre t JOIN t.musical m WHERE m.name LIKE %:keyword% " +
            "OR t.name LIKE %:keyword% ")
    Page<Theatre> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
