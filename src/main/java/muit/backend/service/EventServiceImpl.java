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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MusicalRepository musicalRepository;

    @Override
    public EventResponseDTO.EventResultListDTO getEvent(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId).orElse(null);
        List<Event> eventList = eventRepository.findByMusicalIdOrderByEvFromAsc(musicalId);
        assert musical != null;
        return EventConverter.toEventResultListDTO(musical,eventList);
    }

    @Override
    public Page<EventResponseDTO.EventResultListDTO> getEventListOrderByEvFrom(LocalDate today, Integer page) {
        //Event 를 EvFrom 기준 오름차순으로 정렬
        List<Event> eventList = eventRepository.findAllByEvFromIsNotNullOrderByEvFromAsc();
        List<EventResponseDTO.EventResultListDTO> eventResultListDTOs = eventList.stream()
                .collect(Collectors.groupingBy(event -> event.getMusical().getId()))
                .values().stream()
                .filter(group -> group.stream()                                     // List<Event>로 변환된 스트림을 다시 스트림으로 변환
                        .anyMatch(event -> !event.getEvFrom().isBefore(today)))
                .map(group-> {
                    return EventConverter.toEventResultListDTO(group.get(0).getMusical(), group);})
                .collect(Collectors.toList());


        Pageable pageable = PageRequest.of(page, 6); // pageNumber는 0부터 시작, pageSize는 한 페이지에 몇 개의 그룹을 포함할지
        int start = Math.min((int) pageable.getOffset(), eventResultListDTOs.size());
        int end = Math.min((start + pageable.getPageSize()), eventResultListDTOs.size());
        List<EventResponseDTO.EventResultListDTO> pagedEventResultListDTOs = eventResultListDTOs.subList(start, end);

        // 3. 페이지 객체 반환
        return new PageImpl<>(pagedEventResultListDTOs, pageable, eventResultListDTOs.size());
    }


    @Override
    @Transactional
    public EventResponseDTO.EventResultDTO createEvent(Long musicalId, EventRequestDTO.EventCreateDTO eventCreateDTO) {
        Musical musical = musicalRepository.findById(musicalId).orElse(null);
        Event event = EventConverter.toEvent(eventCreateDTO, musical);
        eventRepository.save(event);
        return EventConverter.toEventResultDTO(event);
    }

    @Override
    public EventResponseDTO.EventResultDTO getEventInfo(Long eventId){
        Event event = eventRepository.findById(eventId).orElse(null);
        return EventConverter.toEventResultDTO(event);
    }
}
