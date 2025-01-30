package muit.backend.repository;

import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByNameContaining(String name);

    Page<Theatre> findAll(Pageable pageable);
}
