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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
