package muit.backend.service.musicalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.config.InterparkConfig;
import muit.backend.config.KopisConfig;
import muit.backend.converter.CastingConverter;
import muit.backend.converter.EventConverter;
import muit.backend.converter.MusicalConverter;
import muit.backend.domain.entity.musical.Actor;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.castingDTO.CastingResponseDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.repository.ActorRepository;
import muit.backend.repository.CastingRepository;
import muit.backend.repository.EventRepository;
import muit.backend.repository.MusicalRepository;
import muit.backend.service.theatreService.TheatreService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalServiceImpl implements MusicalService {
    private final MusicalRepository musicalRepository;
    private final EventRepository eventRepository;
    private final KopisConfig kopisConfig;
    private final TheatreService theatreService;
    private final InterparkConfig interparkConfig;
    private final CastingRepository castingRepository;
    private final ActorRepository actorRepository;

    @Override
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicalId) {
        //뮤지컬 유효성 검사
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new RuntimeException("Musical not found"));

        //이벤트 정보를 List<EventResultDTO>로 구성된 EventResultListDTO 에 담아서 반환
        List<Event> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId);
        EventResponseDTO.EventResultListDTO eventResultListDTO = EventConverter.toEventResultListDTO(musical, eventList);

        return MusicalConverter.toMusicalResultDTO(musical, eventResultListDTO);

    }

    @Override
    @Transactional
    public void createMusical(String kopisMusicalId) {
        try {
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
    public MusicalResponseDTO.MusicalHomeListDTO getFiveMusicals() {
        List<Musical> musicals = musicalRepository.findTop5ByOrderByIdAsc();

        return MusicalConverter.toMusicalHomeListDTO(musicals);
    }

    @Override
    public Page<MusicalResponseDTO.MusicalHomeDTO> getAllHotMusicals(Integer page) {
        List<Musical> musicals = musicalRepository.findAllByOrderByIdAsc();

        List<MusicalResponseDTO.MusicalHomeDTO> musicalHomes = musicals.stream().map(MusicalConverter::toMusicalHomeDTO)
                .toList();

        Pageable pageable = PageRequest.of(page, 20);

        return new PageImpl<>(musicalHomes, pageable, musicalHomes.size());

    }

    @Override
    public MusicalResponseDTO.MusicalOpenListDTO getFiveOpenMusicals() {
        Pageable pageable = PageRequest.of(0, 5); //첫 페이지 5개만 보이도록 함
        List<Musical> musicals = musicalRepository.getFiveOpenWithin7Days(pageable);

        return MusicalConverter.toMusicalOpenListDTO(musicals);
    }

    @Override
    public MusicalResponseDTO.MusicalOpenListDTO getAllOpenMusicals(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        List<Musical> musicals = musicalRepository.getAllOpenAfterToday(pageable);

        return MusicalConverter.toMusicalOpenListDTO(musicals);
    }

    @Override
    public MusicalResponseDTO.MusicalHomeListDTO findMusicalsByName(String musicalName) {
        List<Musical> musicals = musicalRepository.findByNameContaining(musicalName);

        return MusicalConverter.toMusicalHomeListDTO(musicals);
    }

    @Override
    public List<String> getWeeklyRanking() {
        String rankingUrl = interparkConfig.getRankingUrl();
        try {
            Document doc = Jsoup.connect(rankingUrl).get();

            List<String> rankingList = new ArrayList<>();
            Elements elements = doc.select("#contents > article:nth-child(3) > section > div > div > div.responsive-ranking-list_rankingListWrap__GM0yK.responsive-ranking-list_topRated__axfTY > div:nth-child(1) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("1. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div.responsive-ranking-list_rankingListWrap__GM0yK.responsive-ranking-list_topRated__axfTY > div:nth-child(2) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("2. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div.responsive-ranking-list_rankingListWrap__GM0yK.responsive-ranking-list_topRated__axfTY > div:nth-child(3) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("3. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(1) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("4. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(2) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("5. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(3) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("6. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(4) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("7. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(5) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("8. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(6) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("9. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            elements = doc.select("#contents > article:nth-child(3) > section > div > div > div:nth-child(2) > div:nth-child(7) > div.responsive-ranking-list_rankingItemInner__mMLxe > ul > div.responsive-ranking-list_rankingContentsInner__8FuZE > li");
            if (!elements.isEmpty()) {
                rankingList.add("10. " + elements.get(0).text());
            } else {
                System.out.println("선택된 요소가 없습니다.");
            }

            System.out.println(rankingList);
            return rankingList;

        } catch (IOException e) {
            throw new RuntimeException("사이트 접속 실패", e);
        }
    }

    @Override
    public Page<MusicalResponseDTO.AdminMusicalDTO> getAllMusicals(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Musical> musicals = musicalRepository.findAll(pageable);

        List<MusicalResponseDTO.AdminMusicalDTO> musicalDTOS = musicals.stream()
                .map(MusicalConverter::toAdminMusicalDTO)
                .collect(toList());

        return new PageImpl<>(musicalDTOS, pageable, musicalDTOS.size());
    }

    @Override
    public MusicalResponseDTO.AdminMusicalDetailDTO getMusicalDetail(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId).orElse(null);

        List<Event> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId);

        assert musical != null;

        EventResponseDTO.EventResultListDTO eventResultListDTO = EventConverter.toEventResultListDTO(musical, eventList);

        return MusicalConverter.adminMusicalDetailDTO(musical, eventResultListDTO);
    }

    @Override
    public List<CastingResponseDTO.CastingResultListDTO> getCastingInfo(Long musicalId) {

        List<Actor> actors = actorRepository.findAll();
        List<CastingResponseDTO.CastingResultListDTO> castingResultListDTOs = actors.stream()
                .collect(Collectors.groupingBy(actor -> actor.getCasting().getId())).values().stream()
                .map(group -> {
                    return CastingConverter.toCastingResultListDTO(group, group.get(0).getCasting());
                }).toList()
                .stream()
                .filter(castingResultListDTO-> castingResultListDTO.getMusicalId().equals(musicalId)).toList();

        return castingResultListDTOs;

    }
}
