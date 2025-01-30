package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import muit.backend.service.theatreService.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
