package muit.backend.service;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.musical.Event;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public interface EventService {
    public EventResponseDTO.EventResultListDTO getEvent (Long musicalId, Member member);

    public Page<EventResponseDTO.EventResultListDTO> getEventListOrderByEvFrom (LocalDate today, Member member, Integer page);

    public EventResponseDTO.EventResultDTO createEvent(Long musicalId, EventRequestDTO.EventCreateDTO eventCreateDTO);

    public EventResponseDTO.EventResultDTO getEventInfo(Long eventId);
}
