package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.service.EventService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/")
    @Operation(summary = "이벤트 조회 API", description = "시작 날짜가 오늘 날짜 이후인 이벤트를 하나라도 갖고 있는 모든 뮤지컬의 이벤트 목록을 조회하는 API")
    public ApiResponse<EventResponseDTO.EventGroupListDTO> getEventListSortedByEvFrom(){
        LocalDate today = LocalDate.now();
        return ApiResponse.onSuccess(eventService.getEventListOrderByEvFrom(today));
    }

    @GetMapping("/{musicalId}")
    @Operation(summary = "이벤트 조회 - 단건 API", description = "특정 뮤지컬의 이벤트를 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "musicalId", description = "이벤트 정보를 알고 싶은 뮤지컬id 입력")
    })
    public ApiResponse<EventResponseDTO.EventResultListDTO> getEvent(@PathVariable("musicalId") Long musicalId) {
        return ApiResponse.onSuccess(eventService.getEvent(musicalId));
    }
}
