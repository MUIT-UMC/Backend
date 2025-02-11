package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.castingDTO.CastingResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.service.musicalService.MusicalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "뮤지컬")
@RestController
@RequiredArgsConstructor
@RequestMapping("/musicals")
public class MusicalController {
    private final MusicalService musicalService;

    @GetMapping("/{musicalId}")
    @Operation(summary = "뮤지컬 단건 조회 API", description = "특정 뮤지컬을 조회하는 API 입니다.")
    public ApiResponse<MusicalResponseDTO.MusicalResultDTO> getMusical(@PathVariable("musicalId") Long musicalId) {
        return ApiResponse.onSuccess(musicalService.getMusical(musicalId));
    }


    @GetMapping("/hot")
    @Operation(summary = "뮤지컬 조회 - 리스트 HOT NOW", description = "Home에서 현재 HOT한 뮤지컬 5개 조회하는 API, HOT한 기준은 불명")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getFiveHotMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveMusicals());
    }

    @GetMapping("/hot/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 HOT NOW 전체보기", description = "현재 HOT한 뮤지컬 전체 조회하는 API, 페이지당 20개")
    @Parameter( name = "page", description = "페이지를 정수로 입력")
    public ApiResponse<Page<MusicalResponseDTO.MusicalHomeDTO>> getAllHotMusicals(@RequestParam(defaultValue = "0", name = "page") Integer page) {

        return ApiResponse.onSuccess(musicalService.getAllHotMusicals(page));
    }

    @GetMapping("/rank")
    @Operation(summary = "뮤지컬 조회 - 리스트 RANKING", description = "Home에서 Ranking 5개 뮤지컬 조회하는 API, Ranking은 playDB 기준")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getFiveRankMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveMusicals());
    }

    @GetMapping("/rank/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 RANKING 전체보기", description = "RANKING 뮤지컬 전체 조회하는 API, 페이지당 20개")
    public ApiResponse<Page<MusicalResponseDTO.MusicalHomeDTO>> getAllRankMusicals(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ApiResponse.onSuccess(musicalService.getAllHotMusicals(page));
    }

    @GetMapping("/open")
    @Operation(summary = "뮤지컬 조회 - 리스트 TICKET OPEN", description = "Home에서 1주일 안에 오픈하는 최대 5개 뮤지컬을 조회하는 API")
    public ApiResponse<List<MusicalResponseDTO.MusicalOpenDTO>> getFiveOpenMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveOpenMusicals());
    }

    @GetMapping("/open/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 TICKET OPEN 전체보기", description = "오늘 이후 티켓 오픈하는 뮤지컬 전체 조회하는 API, 페이지당 20개")
    public ApiResponse<Page<MusicalResponseDTO.MusicalOpenDTO>> getAllOpenMusicals(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ApiResponse.onSuccess(musicalService.getAllOpenMusicals(page));
    }

    @GetMapping("/open/today")
    @Operation(summary = "뮤지컬 조회 - TICKET OPEN 전체보기에서 오늘 티켓 오픈하는 뮤지컬", description = "오늘 티켓 오픈하는 뮤지컬을 조회하는 API")
    public ApiResponse<List<MusicalResponseDTO.MusicalTodayOpenDTO>> getTodayOpenMusicals() {
        return ApiResponse.onSuccess(musicalService.getTodayOpenMusicals());
    }

    @GetMapping("/search")
    @Operation(summary = "뮤지컬 검색", description = "상단바, 이벤트 확인에서 뮤지컬을 검색하는 API 입니다.")
    @Parameters({
            @Parameter(name = "musicalName", description = "뮤지컬 이름을 검색어로 입력")
    })
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> searchMusicals(@RequestParam(name = "musicalName", required = false) String musicalName) {
        return ApiResponse.onSuccess(musicalService.findMusicalsByName(musicalName));
    }

    @GetMapping("/{musicalId}/casting")
    @Operation(summary = "특정 뮤지컬의 캐스팅 조회 ", description = "뮤지컬의 캐스팅 정보 조회하는 API")
    public ApiResponse<List<CastingResponseDTO.CastingResultListDTO>> getCastingInfo(@PathVariable Long musicalId) {
        return ApiResponse.onSuccess(musicalService.getCastingInfo(musicalId));
    }

}
