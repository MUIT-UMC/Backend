package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import muit.backend.dto.eventDTO.EventRequestDTO;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.service.EventService;
import muit.backend.service.adminService.ManageEventService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 이벤트 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class ManageEventController {
    private final ManageEventService manageEventService;
    private final EventService eventService;

    @GetMapping("")
    @Operation(summary = "관리자 기능 중 이벤트 관리 초기 화면", description = "DB의 전체 뮤지컬 항목을 조회하는 API (이벤트 존재하는 뮤지컬 우선 정렬)")
    public ApiResponse<Page<ManageEventResponseDTO.ManageEventResultListDTO>> getAllMusicals(@ParameterObject Pageable pageable,
                                                                                             @RequestParam(required = false) String keyword,
                                                                                             @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageEventResponseDTO.ManageEventResultListDTO> events = manageEventService.getAllMusicals(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(events);
    }

    @GetMapping("/{musicalId}")
    @Operation(summary = "이벤트 관리에서 특정 뮤지컬 상세 화면", description = "이벤트 관리 - 상세 버튼 클릭 - 해당 뮤지컬의 이벤트들을 조회하는 API")
    @Parameters({
            @Parameter(name = "musicalId", description = "이벤트 정보를 알고 싶은 뮤지컬id 입력")
    })
    public ApiResponse<ManageEventResponseDTO.ManageEventResultDTO> getEvent(@PathVariable("musicalId") Long musicalId) {
        return ApiResponse.onSuccess(manageEventService.getEvent(musicalId));
    }

    @PostMapping("/{musicalId}")
    @Operation(summary = "특정 뮤지컬의 이벤트를 추가하는 API", description = "이벤트 관리 - 상세 - (+)로 폼 채운 후 적용하기 버튼 클릭 - 해당 뮤지컬의 이벤트를 생성하는 API")
    @Parameters({
            @Parameter(name = "musicalId", description = "이벤트를 생성하고 싶은 뮤지컬id 입력")
    })
    public ApiResponse<EventResponseDTO.EventResultDTO> createEvent(@PathVariable("musicalId") Long musicalId, @RequestBody EventRequestDTO.EventCreateDTO eventCreateDTO) {
        return ApiResponse.onSuccess(eventService.createEvent(musicalId, eventCreateDTO));
    }

}
