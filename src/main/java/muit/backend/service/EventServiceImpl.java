package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.EventConverter;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.repository.EventRepository;
import muit.backend.repository.MusicalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MusicalRepository musicalRepository;

    @Override
    public EventResponseDTO.EventResultListDTO getEvent(Long musicalId) {
        List<Event> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId);
        return EventConverter.toEventResultListDTO(eventList);
    }

    @Override
    public EventResponseDTO.EventGroupListDTO getEventListOrderByEvFrom(LocalDate today) {
        //Event 를 EvFrom 기준 오름차순으로 정렬
        List<Event> eventList = eventRepository.findAllByEvFromIsNotNullOrderByEvFromAsc();

        List<List<Event>> eventListGroupedByMusicalId = eventList.stream()
                .collect(Collectors.groupingBy(event->event.getMusical().getId()))  // musicalId로 그룹화
                .values().stream()                                                  // 그룹화된 Map의 values를 가져옵니다 (각 그룹은 List<Event> 형태)
                .filter(group -> group.stream()                                     // List<Event>로 변환된 스트림을 다시 스트림으로 변환
                        .anyMatch(event -> !event.getEvFrom().isBefore(today)))     //evFrom이 today보다 앞선다면의 부정
                .collect(Collectors.toList()); // 최종적으로 List<List<Event>>로 변환


        return EventConverter.toEventGroupListDTO(eventListGroupedByMusicalId);
    }

    @Override
    @Transactional
    public EventResponseDTO.EventResultDTO createEvent(Long musicalId, EventRequestDTO.EventCreateDTO eventCreateDTO) {
        Musical musical = musicalRepository.findById(musicalId).orElse(null);
        Event event = EventConverter.toEvent(eventCreateDTO, musical);
        eventRepository.save(event);
        return EventConverter.toEventResultDTO(event);
    }
}
