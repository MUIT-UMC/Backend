package muit.backend.converter;

import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EventConverter {
    //특정 Event 단건 반환
    public static EventResponseDTO.EventResultDTO toEventResultDTO(Event event) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d (E)", Locale.KOREA);

        String duration = event != null ? event.getEvFrom().format(formatter) + " ~ " + event.getEvTo().format(formatter) : null;
        return EventResponseDTO.EventResultDTO.builder()
                .id(event != null ? event.getId() : null)
                .musicalId(event != null ? event.getMusical().getId() : null)
                .name(event != null ? event.getName() : null)
                .duration(event != null ? duration : null)
                .evFrom(event != null ? event.getEvFrom() : null)
                .evTo(event != null ? event.getEvTo() : null)
                .build();
    }

    //특정 뮤지컬의 여러 Event 리스트로 반환
    public static EventResponseDTO.EventResultListDTO toEventResultListDTO(Musical musical, Boolean isLike, List<Event> eventList) {
        List<EventResponseDTO.EventResultDTO> eventResultDTOs = eventList != null ? eventList.stream()
                .map(EventConverter::toEventResultDTO).toList() : null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA);

        String duration = eventList != null ? musical.getPerFrom().format(formatter) + " ~ " + musical.getPerTo().format(formatter) : null;

        return EventResponseDTO.EventResultListDTO.builder()
                .musicalId(musical.getId())
                .posterUrl(musical.getPosterUrl())
                .musicalName(musical.getName())
                .place(musical.getPlace())
                .duration(duration)
                .perFrom(musical.getPerFrom())
                .perTo(musical.getPerTo())
                .isLike(isLike)
                .eventResultListDTO(eventResultDTOs)
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
