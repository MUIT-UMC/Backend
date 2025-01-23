package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theatres")
public class TheatreController {
    private final TheatreService theatreService;

    @GetMapping ("/")
    @Operation(summary = "공연장 검색", description = "시야확인에서 공연장을 검색하는 API 입니다.")
    @Parameters({
            @Parameter(name = "theatreName", description = "공연장 이름을 검색어로 입력")
    })
    public ApiResponse<TheatreResponseDTO.TheatreResultListDTO> getTheatre(@RequestParam("theatreName") String theatreName) {
        return ApiResponse.onSuccess(theatreService.findTheatreByName(theatreName));
    }

    @GetMapping ("/{theatreId}")
    @Operation(summary = "공연장 좌석 조회", description = "특정 공연장의 좌석 정보를 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "theatreId", description = "좌석 조회할 공연장 id"),
            @Parameter(name = "sectionType", description = "A,B,C,D... 조회할 자리가 속한 섹션 선택")
    })
    public ApiResponse<SectionResponseDTO.SectionResultDTO> getSeatInfo(@PathVariable("theatreId") Long theatreId, @RequestParam("sectionType") SectionType sectionType) {
        return ApiResponse.onSuccess(theatreService.getSection(theatreId, sectionType));
    }

}
