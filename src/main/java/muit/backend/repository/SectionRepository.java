package muit.backend.repository;

import lombok.RequiredArgsConstructor;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.enums.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByTheatreIdAndSectionType(Long TheatreId, SectionType sectionType);
}
