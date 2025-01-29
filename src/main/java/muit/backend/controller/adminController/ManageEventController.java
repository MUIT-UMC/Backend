package muit.backend.controller.adminController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventRequestDTO;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
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

    @Operation(summary = "이벤트가 존재하는 전체 뮤지컬 조회")
    @GetMapping
    public ApiResponse<Page<ManageEventResponseDTO.ManageEventResultListDTO>> getAllEvents(@ParameterObject Pageable pageable,
                                                                                           @RequestParam(required = false) String keyword,
                                                                                           @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageEventResponseDTO.ManageEventResultListDTO> events = manageEventService.getAllEvents(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(events);
    }

    @Operation(summary = "특정 뮤지컬 이벤트 상세보기")
    @GetMapping("/{musicalId}")
    public ApiResponse<ManageEventResponseDTO.ManageEventResultDTO> getEvent(@PathVariable("musicalId") Long musicalId) {
        ManageEventResponseDTO.ManageEventResultDTO event = manageEventService.getEvent(musicalId);
        return ApiResponse.onSuccess(event);
    }

    @Operation(summary = "상세보기 페이지에서 뮤지컬 이벤트 추가")
    @PostMapping("/{musicalId}/add") // 특정 뮤지컬에 이벤트 추가
    public ApiResponse<ManageEventResponseDTO.ManageEventResultDTO> addEvent(@PathVariable("musicalId") Long musicalId, @RequestBody ManageEventRequestDTO.ManageEventAddDTO requestDTO) {
        ManageEventResponseDTO.ManageEventResultDTO event = manageEventService.addEvent(musicalId, requestDTO);
        return ApiResponse.onSuccess(event);
    }

    @Operation(summary = "추가하기 버튼으로 이벤트가 있는 뮤지컬 생성")
    @PostMapping("/create")// 이벤트가 있는 뮤지컬 생성
    public ApiResponse<ManageEventResponseDTO.ManageEventResultDTO> createEvent(@RequestBody ManageEventRequestDTO.ManageEventCreateDTO requestDTO) {
        ManageEventResponseDTO.ManageEventResultDTO event = manageEventService.createEvent(requestDTO);
        return ApiResponse.onSuccess(event);
    }
}
