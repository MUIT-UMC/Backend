package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.EventConverter;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.repository.EventRepository;
import muit.backend.repository.MusicalRepository;
import muit.backend.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalServiceImpl implements MusicalService {
    private final MusicalRepository musicalRepository;
    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;

    @Override
    public MusicalResponseDTO.MusicalResultDTO getMusical(Long musicalId){
        //뮤지컬 유효성 검사
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new RuntimeException("Musical not found"));


        //배우 이름을 List<String>로 뽑아냄
        List<String> actorList = musical.getActorNameAsStringList();
        //가격 정보 List<String> 로 뽑아냄(VIP>R>S>A>B 순서)
        List<String> priceList = musical.getPrice().getPriceAsStringList();

        //이벤트 정보를 List<EventResultDTO>로 구성된 EventResultListDTO 에 담아서 반환
        List<Event> eventList = eventRepository.findByMusicalId(musicalId);
        EventResponseDTO.EventResultListDTO eventResultListDTO = EventConverter.toEventResultListDTO(eventList);

        return MusicalResponseDTO.MusicalResultDTO.builder()
                .id(musical.getId())
                .desImgUrl(musical.getDesImgUrl())
                .posterUrl(musical.getPosterUrl())
                .musicalName(musical.getName())
                .theatreName(musical.getTheatre().getName())
                .perPattern(musical.getPerPattern())
                .runTime(musical.getRuntime())
                .ageLimit(musical.getAgeLimit())
                .actorList(actorList)
                .discountInfo("공동구매 시 20% 할인")
                .priceInfo(priceList)
                .eventList(eventResultListDTO)
                .description(musical.getDescription())
                .desImg2Url(musical.getDesImg2Url())
                .build();
    }
}
