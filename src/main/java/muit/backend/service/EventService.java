package muit.backend.service;

import muit.backend.domain.entity.musical.Event;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public interface EventService {
    public EventResponseDTO.EventResultListDTO getEvent (Long musicalId);

    public List<EventResponseDTO.EventResultListDTO> getEventListOrderByEvFrom (LocalDate today);

    public EventResponseDTO.EventResultDTO createEvent(Long musicalId, EventRequestDTO.EventCreateDTO eventCreateDTO);
}
