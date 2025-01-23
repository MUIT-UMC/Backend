package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowResponseDTO;
import muit.backend.service.ManageAmateurShowService;
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
    public ApiResponse<Page<ManageAmateurShowResponseDTO.ResultListDTO>> getAllAmateurShows(@ParameterObject Pageable pageable,
                                                                                            @RequestParam(required = false) String keyword,
                                                                                            @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageAmateurShowResponseDTO.ResultListDTO> amateurShows = manageAmateurShowService.getAllAmateurShows(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(amateurShows);
    }

    @Operation(summary = "특정 소극장 공연 상세 조회")
    @GetMapping("/{amateurShowId}")
    public ApiResponse<ManageAmateurShowResponseDTO.ResultDTO> getAmateurShow(@PathVariable("amateurShowId") Long amateurShowId) {
        ManageAmateurShowResponseDTO.ResultDTO amateurShow = manageAmateurShowService.getAmateurShow(amateurShowId);
        return ApiResponse.onSuccess(amateurShow);
    }


}
