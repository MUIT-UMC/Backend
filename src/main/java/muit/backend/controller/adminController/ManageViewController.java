package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.dto.theatreDTO.TheatreResponseDTO;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "어드민이 시야 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/views")
public class ManageViewController {

    private final TheatreService theatreService;

    @GetMapping("")
    @Operation(summary = "관리자 기능 중 시야 관리 초기 화면", description = "DB의 전체 공연장 항목을 조회하는 API")
    public ApiResponse<ManageViewResponseDTO.AdminTheatreResultListDTO> getTheatres(@RequestParam(value = "page", defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 20, Sort.by("id").ascending());
        return ApiResponse.onSuccess(theatreService.getTheatres(pageable));
    }

    @GetMapping("/{theatreId}")
    @Operation(summary = "시야 관리에서 특정 공연장 상세 화면", description = "시야 관리 - 상세 버튼 클릭 - 해당 공연장의 좌석 정보를 조회하는 API")
    @Parameters({
            @Parameter(name = "theatreId", description = "시야 관리할 공연장 ID 입력")
    })
    public ApiResponse<TheatreResponseDTO.AdminTheatreDetailDTO> getTheatre(@PathVariable("theatreId") Long theatreId){
        return ApiResponse.onSuccess(theatreService.getTheatreDetail(theatreId));
    }
}
