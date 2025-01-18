package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.repository.SectionRepository;
import muit.backend.repository.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final SectionRepository sectionRepository;

    @Override
    public TheatreResponseDTO.TheatreResultDTO getTheatre(String theatreName){
        Theatre theatre = theatreRepository.findByName(theatreName)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        return TheatreResponseDTO.TheatreResultDTO.builder()
                .id(theatre.getId())
                .theatreName(theatre.getName())
                .address(theatre.getAddress())
                .pictureUrl(theatre.getPictureUrl())
                .build();
    }

    @Override
    public SectionResponseDTO.SectionResultDTO getSection(Long theatreId, SectionType sectionType){
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Optional<Section> section = sectionRepository.findByTheatreIdAndSectionType(theatreId,sectionType);

        return SectionResponseDTO.SectionResultDTO.builder()
                .theatreId(theatreId)
                .theatreName(theatre.getName())
                .address(theatre.getAddress())
                .musicalId(theatre.getMusical().getId())
                .musicalName(theatre.getMusical().getName())
                .seatUrl(theatre.getSeatUrl())
                .viewPic(section.get().getViewPic())
                .viewDetail(section.get().getViewDetail())
                .sectionType(sectionType)
                .build();
    }


}
