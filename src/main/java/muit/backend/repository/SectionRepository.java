package muit.backend.repository;

import lombok.RequiredArgsConstructor;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.enums.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Section findByTheatreIdAndSectionType(Long TheatreId, SectionType sectionType);

    List<Section> findAllByTheatreIdOrderBySectionTypeAsc(Long TheatreId);

    List<Section> findAllByTheatreIdAndFloorOrderBySectionTypeAsc(Long TheatreId, String floor);

}
