package muit.backend.service;

import muit.backend.dto.eventDTO.EventResponseDTO;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public interface EventService {
    public EventResponseDTO.EventResultListDTO getEvent (Long musicalId);

    public EventResponseDTO.EventGroupListDTO getEventListOrderByEvFrom (LocalDate today);
}
