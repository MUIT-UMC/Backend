package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.Floor;
import muit.backend.domain.enums.SectionType;
import muit.backend.dto.sectionDTO.SectionResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공연장")
@RestController
@RequiredArgsConstructor
@RequestMapping("/theatres")
public class TheatreController {
    private final TheatreService theatreService;

    @GetMapping ("")
    @Operation(summary = "공연장 검색", description = "시야확인에서 공연장을 검색하는 API 입니다.")
    @Parameters({
            @Parameter(name = "theatreName", description = "공연장 이름을 검색어로 입력")
    })
    public ApiResponse<TheatreResponseDTO.TheatreResultListDTO> getTheatre(@RequestParam("theatreName") String theatreName) {
        return ApiResponse.onSuccess(theatreService.findTheatreByName(theatreName));
    }

    @GetMapping("/{theatreId}/floor")
    @Operation(summary = "층 별 섹션 조회", description = "특정 공연장의 층에 따른 섹션을 조회하는 API 입니다.")
    public ApiResponse<SectionResponseDTO.FloorResultDTO> getFloorSection(@PathVariable("theatreId") Long theatreId, @RequestParam("floor")Floor floor){
        return ApiResponse.onSuccess(theatreService.getFloor(theatreId, floor));
    }

    @GetMapping ("/{theatreId}/sectionType")
    @Operation(summary = "섹션 별 좌석 조회", description = "선택한 섹션의 좌석 정보를 조회하는 API 입니다.")
    @Parameters({
            @Parameter(name = "theatreId", description = "좌석 조회할 공연장 id"),
            @Parameter(name = "sectionType", description = "A,B,C,D... 조회할 자리가 속한 섹션 선택")
    })
    public ApiResponse<SectionResponseDTO.SectionResultDTO> getSeatInfo(@PathVariable("theatreId") Long theatreId, @RequestParam("sectionType") SectionType sectionType) {
        return ApiResponse.onSuccess(theatreService.getSection(theatreId, sectionType));
    }



}
