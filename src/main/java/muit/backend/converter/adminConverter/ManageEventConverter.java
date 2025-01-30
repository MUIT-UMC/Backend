package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.musical.Event;
import muit.backend.domain.entity.musical.Musical;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventRequestDTO;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ManageEventConverter {

    public static ManageEventResponseDTO.ManageEventResultListDTO toManageEventResultListDTO(Musical musical, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            return ManageEventResponseDTO.ManageEventResultListDTO.builder()
                    .musicalId(musical.getId())
                    .name(musical.getName())
                    .place(musical.getPlace())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return ManageEventResponseDTO.ManageEventResultListDTO.builder()
                .musicalId(musical.getId())  // ID는 항상 포함
                .name(selectedFields.contains("name") ? musical.getName() : null)
                .place(selectedFields.contains("place") ? musical.getPlace() : null)
                .build();
    }

    public static ManageEventResponseDTO.ManageEventResultDTO toManageEventResultDTO(Musical musical) {

        List<ManageEventResponseDTO.EventInfo> eventInfoList = musical.getEventList().stream()
                .map(event -> new ManageEventResponseDTO.EventInfo(
                        event.getEvFrom(),
                        event.getEvTo(),
                        event.getName()
                ))
                .collect(Collectors.toList());

        return ManageEventResponseDTO.ManageEventResultDTO.builder()
                .musicalId(musical.getId())
                .musicalName(musical.getName())
                .place(musical.getPlace())
                .events(eventInfoList)
                .build();
    }

    public static Event toAddEventEntity(Musical musical, ManageEventRequestDTO.ManageEventAddDTO requestDTO) {
        return Event.builder()
                .musical(musical) // 어떤 뮤지컬에 속한 이벤트인지 설정
                .name(requestDTO.getName())
                .evFrom(requestDTO.getEvFrom())
                .evTo(requestDTO.getEvTo())
                .build();
    }

    public static Event toCreateEventEntity(Musical musical, ManageEventRequestDTO.ManageEventCreateDTO requestDTO) {
        return Event.builder()
                .musical(musical)
                .name(requestDTO.getEventName())
                .evFrom(requestDTO.getEvFrom())
                .evTo(requestDTO.getEvTo())
                .build();
    }
}