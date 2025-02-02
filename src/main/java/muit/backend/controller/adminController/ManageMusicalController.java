package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.service.musicalService.MusicalService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 뮤지컬 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/musicals")
public class ManageMusicalController {

    private final MusicalService musicalService;

    @GetMapping("")
    @Operation(summary = "관리자 기능 중 뮤지컬 관리 초기화면", description = "DB의 전체 뮤지컬 항목을 조회하는 API")
    public ApiResponse<Page<MusicalResponseDTO.AdminMusicalDTO>> getAllMusicals(@RequestParam(defaultValue = "0", name = "page") Integer page) {
        return ApiResponse.onSuccess(musicalService.getAllMusicals(page));
    }

    @GetMapping("/{musicalId}")
    @Operation(summary = "뮤지컬 관리 상세", description = "뮤지컬 정보를 조회하는 API")
    public ApiResponse<MusicalResponseDTO.AdminMusicalDetailDTO> getMusicalDetail(@PathVariable(name = "musicalId") Long musicalId) {
        return ApiResponse.onSuccess(musicalService.getMusicalDetail(musicalId));
    }



}
