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

}
