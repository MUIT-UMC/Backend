package muit.backend.service.theatreService;

import lombok.RequiredArgsConstructor;
import muit.backend.config.KopisConfig;
import muit.backend.converter.MusicalConverter;
import muit.backend.converter.TheatreConverter;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Section;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.kopisDTO.KopisTheatreResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreRequestDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.repository.MusicalRepository;
import muit.backend.repository.SectionRepository;
import muit.backend.repository.TheatreRepository;
import muit.backend.service.musicalService.KopisXmlParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final SectionRepository sectionRepository;
    private final KopisConfig kopisConfig;

    @Override
    public TheatreResponseDTO.TheatreResultListDTO findTheatreByName(String theatreName){
        List<Theatre> theatre = theatreRepository.findByNameContaining(theatreName);

        return TheatreConverter.toTheatreResultListDTO(theatre);
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
                .allSeatImg(theatre.getAllSeatImg())
                .viewDetail(section.get().getViewDetail())
                .viewPic(section.get().getViewPic())
                .sectionType(sectionType)
                .build();
    }

    @Override
    @Transactional
    public Theatre createTheatre(String kopisTheatreId, Musical musical){
        try{
            //Xml -> KopisTheatreResponseDTO
            InputStream inputStream = KopisXmlParser.getOpenApiXmlResponse(kopisConfig.getTheatreInfoUrlFromKopis(kopisTheatreId));
            KopisTheatreResponseDTO.KopisTheatreDTO kopisTheatreDTO = KopisXmlParser.convertTheatreXmlToResponseDTO(inputStream);

            if (kopisTheatreDTO == null) {
                throw new Exception("Kopis API 응답이 잘못되었습니다.");
            }
            inputStream.close();

            // KopisTheatreDTO -> TheatreCreateDTO
            TheatreRequestDTO.TheatreCreateDTO theatreCreateDTO = TheatreConverter.convertKopisDTOToTheatreCreateDTO(kopisTheatreDTO);

            Theatre theatre  = TheatreConverter.toTheatre(theatreCreateDTO, musical);
            theatreRepository.save(theatre);

            return theatre;

        } catch (Exception e) {
            throw new RuntimeException("공연장 저장 실패", e);
        }

    }

}
