package muit.backend.converter;

import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class EventConverter {
    //특정 Event 단건 반환
    public static EventResponseDTO.EventResultDTO toEventResultDTO(Event event) {
        return EventResponseDTO.EventResultDTO.builder()
                .id(event.getId())
                .musicalId(event.getMusical().getId())
                .name(event.getName())
                .evFrom(event.getEvFrom())
                .evTo(event.getEvTo())
                .build();
    }

    //특정 뮤지컬의 여러 Event 리스트로 반환
    public static EventResponseDTO.EventResultListDTO toEventResultListDTO(Musical musical, List<Event> eventList) {
        List<EventResponseDTO.EventResultDTO> eventResultListDTO = eventList.stream()
                .map(EventConverter::toEventResultDTO).collect(Collectors.toList());

        return EventResponseDTO.EventResultListDTO.builder()
                .musicalId(musical.getId())
                .musicalName(musical.getName())
                .theatreName(musical.getTheatre().getName())
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .eventResultListDTO(eventResultListDTO)
                .build();
    }

    public static Event toEvent(EventRequestDTO.EventCreateDTO eventCreateDTO, Musical musical) {
        return Event.builder()
                .musical(musical)
                .name(eventCreateDTO.getEventName())
                .evFrom(eventCreateDTO.getEvFrom())
                .evTo(eventCreateDTO.getEvTo())
                .build();
    }
}
