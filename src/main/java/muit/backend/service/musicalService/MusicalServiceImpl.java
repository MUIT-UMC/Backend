package muit.backend.service.musicalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.config.KopisConfig;
import muit.backend.converter.EventConverter;
import muit.backend.converter.MusicalConverter;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.repository.EventRepository;
import muit.backend.repository.MusicalRepository;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalServiceImpl implements MusicalService {
    private final MusicalRepository musicalRepository;
    private final EventRepository eventRepository;
    private final KopisConfig kopisConfig;
    private final TheatreService theatreService;

    @Override
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicalId){
        //뮤지컬 유효성 검사
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new RuntimeException("Musical not found"));

        //이벤트 정보를 List<EventResultDTO>로 구성된 EventResultListDTO 에 담아서 반환
        List<Event> eventList = eventRepository.findByMusicalId(musicalId);
        EventResponseDTO.EventResultListDTO eventResultListDTO = EventConverter.toEventResultListDTO(eventList);

        return MusicalConverter.toMusicalResultDTO(musical, eventResultListDTO);

    }

    @Override
    @Transactional
    public void createMusical(String kopisMusicalId) {
        try{
            // Kopis url로 HTTP 요청 보냄
            InputStream inputStream = KopisXmlParser.getOpenApiXmlResponse(kopisConfig.getMusicalInfoUrlFromKopis(kopisMusicalId));
            // Kopis에서 응답으로 받은 XML 데이터를 자바 객체로 변환(파싱) :  inputStream (XML) -> KopisMusicalDTO (DTO)
            KopisMusicalResponseDTO.KopisMusicalDTO kopisMusicalDTO = KopisXmlParser.convertMusicalXmlToResponseDTO(inputStream);

            //응답을 못 받았을 경우 예외 처리
            if (kopisMusicalDTO == null) {
                throw new Exception("Kopis API 응답이 잘못되었습니다.");
            }
            // 파싱 종료, 커넥션 닫기
            inputStream.close();

            // Kopis에서 받아온 DTO형식의 뮤지컬 정보를 Muit DB에 넣기 위해 musicalCreateDTO로 변환
            // KopisMusicalDTO -> MusicalCreateDTO
            MusicalRequestDTO.MusicalCreateDTO musicalCreateDTO = MusicalConverter.convertKopisDTOToMusicalCreateDTO(kopisMusicalDTO);

            // MusicalCreateDTO를 기반으로 Converter 이용하여 Musical 엔티티 생성 후 Muit DB에 저장
            Musical musical = MusicalConverter.toMusical(musicalCreateDTO);
            musicalRepository.save(musical);
            // MusicalCreateDTO의 kopisTheatreId 필드를 이용하여 Theatre 엔티티 생성
            Theatre newTheatre = theatreService.createTheatre(musicalCreateDTO.getKopisTheatreId(), musical);
            // 생성된 Theatred의 id를 Musical의 theatreId 필드에 채우기
            musical.updateTheatre(newTheatre);
        } catch (Exception e) {
            throw new RuntimeException("뮤지컬 저장 실패", e);
        }
    }

    @Override
    public MusicalResponseDTO.MusicalHomeListDTO getFiveMusicals(){
        List<Musical> musicals = musicalRepository.findTop5ByOrderByIdAsc();
        String message = "검색 성공";
        if (musicals.isEmpty()) message = "뮤지컬이 존재하지 않습니다";

        return MusicalConverter.toMusicalHomeListDTO(musicals, message);
    }

    @Override
    public MusicalResponseDTO.MusicalHomeListDTO getAllHotMusicals(Integer page){
        Pageable pageable = PageRequest.of(page,20);
        List<Musical> musicals = musicalRepository.findAllByOrderByIdAsc(pageable);
        String message = "검색 성공";
        if (musicals.isEmpty()) message = "뮤지컬이 존재하지 않습니다";
        return MusicalConverter.toMusicalHomeListDTO(musicals, message);
    }

    @Override
    public MusicalResponseDTO.MusicalOpenListDTO getFiveOpenMusicals(){
        Pageable pageable = PageRequest.of(0,5);
        List<Musical> musicals = musicalRepository.getFiveOpenWithin7Days(pageable);

        return MusicalConverter.toMusicalOpenListDTO(musicals);
    }

    @Override
    public MusicalResponseDTO.MusicalOpenListDTO getAllOpenMusicals(Integer page){
        Pageable pageable = PageRequest.of(page,20);
        List<Musical> musicals = musicalRepository.getAllOpenAfterToday(pageable);

        return MusicalConverter.toMusicalOpenListDTO(musicals);
    }

    @Override
    public MusicalResponseDTO.MusicalHomeListDTO findMusicalsByName(String musicalName){
        List<Musical> musicals = musicalRepository.findByNameContaining(musicalName);

        String message = "검색 결과";
        if(musicals.isEmpty()) message = "검색 결과가 존재하지 않습니다.";
        return MusicalConverter.toMusicalHomeListDTO(musicals, message);
    }
}
