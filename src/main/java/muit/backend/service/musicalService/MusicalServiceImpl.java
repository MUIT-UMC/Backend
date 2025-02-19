package muit.backend.service.musicalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.config.InterparkConfig;
import muit.backend.config.KopisConfig;
import muit.backend.converter.CastingConverter;
import muit.backend.converter.EventConverter;
import muit.backend.converter.MusicalConverter;
import muit.backend.domain.entity.member.Likes;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.musical.Actor;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.domain.entity.musical.Theatre;
import muit.backend.domain.enums.PostType;
import muit.backend.dto.castingDTO.CastingResponseDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.kopisDTO.KopisMusicalResponseDTO;
import muit.backend.dto.musicalDTO.MusicalRequestDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.repository.*;
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
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    @Override
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicalId, Member member) {
        //뮤지컬 유효성 검사
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        //이벤트 정보를 List<EventResultDTO>로 구성된 EventResultListDTO 에 담아서 반환
        List<EventResponseDTO.EventResultDTO> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId).stream()
                .map(EventConverter::toEventResultDTO).toList();


        //뮤지컬 평점
        List<Post> posts = postRepository.findAllByPostTypeAndMusicalId(PostType.REVIEW,musicalId);
        double rating=0;
        for(Post post: posts){
            rating+=post.getRating();
        }
        rating = Math.round(10*rating/posts.size())/10.0;

        Boolean isLike = false;
        Likes likes = likesRepository.findByMemberIdAndMusicalId(member.getId(),musicalId);
        if (likes!=null) {
            isLike = true;
        }

        return MusicalConverter.toMusicalResultDTO(musical, eventList, rating, isLike);

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
    public List<MusicalResponseDTO.MusicalOpenDTO> getFiveOpenMusicals() {
        Pageable pageable = PageRequest.of(0, 5); //첫 페이지 5개만 보이도록 함
        List<Musical> musicals = musicalRepository.getFiveOpenWithin7Days(pageable);

        return musicals.stream().map(MusicalConverter::toMusicalOpenDTO).collect(toList());
    }

    @Override
    public Page<MusicalResponseDTO.MusicalOpenDTO> getAllOpenMusicals(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        List<Musical> musicals = musicalRepository.getAllOpenAfterToday(pageable);

        List<MusicalResponseDTO.MusicalOpenDTO> musicalDTOs = musicals.stream().map(MusicalConverter::toMusicalOpenDTO).toList();

        return new PageImpl<>(musicalDTOs, pageable, musicalDTOs.size());
    }

    @Override
    public MusicalResponseDTO.MusicalHomeListDTO findMusicalsByName(String musicalName) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = musicalName != null && !musicalName.trim().isEmpty(); // 빈 검색어도 없다고 침

        List<Musical> musicals;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            musicals = musicalRepository.findByNameContaining(musicalName);
            if (musicals.isEmpty()) {

            }
        } else {// 검색어가 없으면 모든 이벤트 가진 뮤지컬 정보 조회
            musicals = musicalRepository.findAll();
        }
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
    public Page<MusicalResponseDTO.AdminMusicalDTO> getAllMusicals(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 20);

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 빈 검색어도 없다고 침

        Page<Musical> musicals;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            musicals = musicalRepository.findByKeyword(pageable, keyword);
            if (musicals.isEmpty()) {
                return Page.empty(pageable);
            }
        } else {// 검색어가 없으면 모든 이벤트 가진 뮤지컬 정보 조회
            musicals = musicalRepository.findAll(pageable);
        }

        List<MusicalResponseDTO.AdminMusicalDTO> musicalDTOS = musicals.stream()
                .map(MusicalConverter::toAdminMusicalDTO)
                .collect(toList());

        return new PageImpl<>(musicalDTOS, pageable, musicalDTOS.size());
    }

    @Override
    public MusicalResponseDTO.AdminMusicalDetailDTO getMusicalDetail(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        List<Event> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId);


        assert musical != null;

        return MusicalConverter.toAdminMusicalDetailDTO(musical, eventList);
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

    @Override
    public List<MusicalResponseDTO.MusicalTodayOpenDTO> getTodayOpenMusicals() {
        List<Musical> musicals = musicalRepository.getTodayOpenMusicals();
        return musicals.stream().map(MusicalConverter::toMusicalTodayOpenDTO).toList();
    }

    @Transactional
    @Override
    public MusicalResponseDTO.MusicalHomeDTO likeMusical(Member member, Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        Long memberId = member.getId();

        Likes likes = likesRepository.findByMemberIdAndMusicalId(memberId, musicalId);
        if(likes != null) {
            return MusicalResponseDTO.MusicalHomeDTO.builder()
                    .id(likes.getMusical().getId())
                    .name(likes.getMusical().getName())
                    .msg("이미 좋아요한 뮤지컬입니다")
                    .build();
        }

        Likes newLikes = Likes.builder()
                .member(member)
                .musical(musical)
                .build();
        likesRepository.save(newLikes);

        return MusicalConverter.toMusicalHomeDTO(musical);
    }

    @Transactional
    @Override
    public MusicalResponseDTO.MusicalHomeDTO likeCancelMusical(Member member, Long musicalId){
        Long memberId = member.getId();

        Musical musical = musicalRepository.findById(musicalId).orElseThrow(()->new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        likesRepository.deleteByMemberIdAndMusicalId(memberId, musicalId);

        return MusicalResponseDTO.MusicalHomeDTO.builder()
                .id(musicalId)
                .name(musical.getName())
                .msg("좋아요가 취소되었습니다")
                .build();
    }

}
