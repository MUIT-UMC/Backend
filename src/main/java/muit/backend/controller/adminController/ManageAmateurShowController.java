package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.AmateurStatus;
import muit.backend.dto.adminDTO.manageAmateurShowDTO.ManageAmateurShowRequestDTO;
import muit.backend.dto.adminDTO.manageAmateurShowDTO.ManageAmateurShowResponseDTO;
import muit.backend.service.adminService.ManageAmateurShowService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 소극장 공연 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/amateur-shows")
public class ManageAmateurShowController {

    private final ManageAmateurShowService manageAmateurShowService;

    @Operation(summary = "전체 소극장 공연 조회")
    @GetMapping
    public ApiResponse<Page<ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO>> getAllAmateurShows(@ParameterObject Pageable pageable,
                                                                                                             @RequestParam(required = false) String keyword,
                                                                                                             @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO> amateurShows = manageAmateurShowService.getAllAmateurShows(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(amateurShows);
    }

    @Operation(summary = "특정 소극장 공연 상세 조회")
    @GetMapping("/{amateurShowId}")
    public ApiResponse<ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO> getAmateurShow(@PathVariable("amateurShowId") Long amateurShowId) {
        ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO amateurShow = manageAmateurShowService.getAmateurShow(amateurShowId);
        return ApiResponse.onSuccess(amateurShow);
    }


    @Operation(summary = "특정 소극장 공연 정보 수정")
    @PatchMapping("/{amateurShowId}/update")
    public ApiResponse<ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO> updateAmateurShow(@PathVariable("amateurShowId") Long amateurShowId, @RequestBody ManageAmateurShowRequestDTO.ManageAmateurShowUpdateDTO requestDTO) {
        ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO amateurShow = manageAmateurShowService.updateAmateurShow(amateurShowId, requestDTO);
        return ApiResponse.onSuccess(amateurShow);
    }

    @Operation(summary = "소극장 공연 최종 등록/반려")
    @PutMapping("/{amateurShowId}/decision")
    public ApiResponse<ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO> decideAmateurShow(@PathVariable("amateurShowId") Long amateurShowId, @RequestParam AmateurStatus amateurStatus, @RequestBody ManageAmateurShowRequestDTO.ManageAmateurShowDecideDTO requestDTO) {
        ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO amateurShow = manageAmateurShowService.decideAmateurShow(amateurShowId, amateurStatus, requestDTO);
        return ApiResponse.onSuccess(amateurShow);
    }

    @Operation(summary = "소극장 공연 등록/반려 조회", description = "특정 소극장 공연 등록/반려 페이지 조회")
    @GetMapping("/{amateurShowId}/decision")
    public ApiResponse<ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO> getDecideAmateurShow(@PathVariable("amateurShowId") Long amateurShowId) {
        return ApiResponse.onSuccess(manageAmateurShowService.getDecideAmateurShow(amateurShowId));
    }

}

