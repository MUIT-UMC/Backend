package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.dto.postDTO.PostResponseDTO;
import muit.backend.service.MusicalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
