package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.eventDTO.EventResponseDTO;
import muit.backend.service.EventService;

import muit.backend.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "이벤트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final MemberService memberService;

    @GetMapping("")
    @Operation(summary = "현재 진행중인 뮤지컬 이벤트 조회 API", description = "시작 날짜가 오늘 날짜 이후인 이벤트를 하나라도 갖고 있는 모든 뮤지컬의 이벤트 목록을 조회하는 API, 한 페이지에 뮤지컬 6개씩")
    public ApiResponse<Page<EventResponseDTO.EventResultListDTO>> getEventListSortedByEvFrom(@RequestHeader("Authorization") String accessToken,
                                                                                             @RequestParam(defaultValue = "0", name = "page") Integer page){
        LocalDate today = LocalDate.now();
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(eventService.getEventListOrderByEvFrom(today, page));
    }

    @GetMapping("/{musicalId}")
    @Operation(summary = "특정 뮤지컬의 이벤트 목록 조회 API", description = "특정 뮤지컬의 이벤트를 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "musicalId", description = "이벤트 정보를 알고 싶은 뮤지컬id 입력")
    })
    public ApiResponse<EventResponseDTO.EventResultListDTO> getEvent(@RequestHeader("Authorization") String accessToken,
                                                                     @PathVariable("musicalId") Long musicalId) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(eventService.getEvent(musicalId));
    }

    @GetMapping("/musical/{eventId}")
    @Operation(summary = "특정 뮤지컬의 특정 이벤트 조회 API", description = "특정 이벤트의 날짜 정보를 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "eventId", description = "날짜 정보를 알고 싶은 eventId 입력")
    })
    public ApiResponse<EventResponseDTO.EventResultDTO> getEvInfo(@RequestHeader("Authorization") String accessToken,
                                                                  @PathVariable("eventId") Long eventId) {
        Member member = memberService.getMemberByToken(accessToken);
        return ApiResponse.onSuccess(eventService.getEventInfo(eventId));
    }
}
