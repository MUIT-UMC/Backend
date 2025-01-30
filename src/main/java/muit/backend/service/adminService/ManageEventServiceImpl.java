package muit.backend.service.adminService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.adminConverter.ManageEventConverter;
import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventRequestDTO;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import muit.backend.repository.EventRepository;
import muit.backend.repository.MusicalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageEventServiceImpl implements ManageEventService {

    private final MusicalRepository musicalRepository;
    private final EventRepository eventRepository;

    // 전체 이벤트 조회
    @Override
    public Page<ManageEventResponseDTO.ManageEventResultListDTO> getAllEvents(Pageable pageable, String keyword, Set<String> selectedFields) {

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
            musicals = musicalRepository.findAllWithEvents(pageable);
        }

        return musicals.map(musical -> ManageEventConverter.toManageEventResultListDTO(musical, selectedFields, isKeywordSearch));
    }

    // 특정 이벤트 조회
    @Override
    public ManageEventResponseDTO.ManageEventResultDTO getEvent(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        return ManageEventConverter.toManageEventResultDTO(musical);
    }

    // 특정 뮤지컬에 이벤트 추가 (상세페이지)
    @Override
    @Transactional
    public ManageEventResponseDTO.ManageEventResultDTO addEvent(Long musicalId, ManageEventRequestDTO.ManageEventAddDTO requestDTO) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        Event event = ManageEventConverter.toAddEventEntity(musical, requestDTO);
        eventRepository.save(event);
        return ManageEventConverter.toManageEventResultDTO(musical);
    }

    // 이벤트가 있는 뮤지컬 생성
    @Override
    @Transactional
    public ManageEventResponseDTO.ManageEventResultDTO createEvent(ManageEventRequestDTO.ManageEventCreateDTO requestDTO) {
        Musical musical = musicalRepository.findByName(requestDTO.getMusicalName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MUSICAL_NOT_FOUND));

        Event event = ManageEventConverter.toCreateEventEntity(musical, requestDTO);
        eventRepository.save(event);
        return ManageEventConverter.toManageEventResultDTO(musical);
    }

}
