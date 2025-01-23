package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.service.musicalService.MusicalService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getMusicalInfoFromKopis")
    @Operation(summary = "Kopis로부터 뮤지컬 정보 추출하는 API", description = "Kopis 에서 정보를 가져오고 싶은 뮤지컬의 Kopis Id를 입력 (미아 파밀리아 = PF253515)")
    public ApiResponse<String> createMusical(@RequestParam("kopisMusicalId") String kopisMusicalId) {

        musicalService.createMusical(kopisMusicalId);
        return ApiResponse.onSuccess("뮤지컬 정보를 성공적으로 저장하였습니다.");
    }

    @GetMapping("/hot")
    @Operation(summary = "뮤지컬 조회 - 리스트 HOT NOW", description = "Home에서 현재 HOT한 뮤지컬 5개 조회하는 API, HOT한 기준은 불명")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getFiveHotMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveMusicals());
    }

    @GetMapping("/hot/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 HOT NOW 전체보기", description = "현재 HOT한 뮤지컬 전체 조회하는 API")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getAllHotMusicals() {
        return ApiResponse.onSuccess(musicalService.getAllHotMusicals());
    }

    @GetMapping("/rank")
    @Operation(summary = "뮤지컬 조회 - 리스트 RANKING", description = "Home에서 Ranking 5개 뮤지컬 조회하는 API, Ranking 기준은 불명")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getFiveRankMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveMusicals());
    }

    @GetMapping("/rank/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 RANKING 전체보기", description = "RANKING 뮤지컬 전체 조회하는 API")
    public ApiResponse<MusicalResponseDTO.MusicalHomeListDTO> getAllRankMusicals() {
        return ApiResponse.onSuccess(musicalService.getAllHotMusicals());
    }

    @GetMapping("/open")
    @Operation(summary = "뮤지컬 조회 - 리스트 TICKET OPEN", description = "Home에서 1주일 안에 오픈하는 최대 5개 뮤지컬을 조회하는 API")
    public ApiResponse<MusicalResponseDTO.MusicalOpenListDTO> getFiveOpenMusicals() {
        return ApiResponse.onSuccess(musicalService.getFiveOpenMusicals());
    }

    @GetMapping("/open/all")
    @Operation(summary = "뮤지컬 조회 - 리스트 TICKET OPEN 전체보기", description = "오늘 이후 티켓 오픈하는 뮤지컬 전체 조회하는 API")
    public ApiResponse<MusicalResponseDTO.MusicalOpenListDTO> getAllOpenMusicals() {
        return ApiResponse.onSuccess(musicalService.getAllOpenMusicals());
    }
}
