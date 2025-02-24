package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.manageEventDTO.ManageEventResponseDTO;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.adminService.ManageEventService;
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
    private final MemberService memberService;

    @GetMapping("")
    @Operation(summary = "관리자 기능 중 뮤지컬 관리 초기화면", description = "DB의 전체 뮤지컬 항목을 조회하는 API")
    public ApiResponse<Page<MusicalResponseDTO.AdminMusicalDTO>> getAllMusicals(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @RequestParam(name = "keyword", required = false) String keyword,
                                                                                @RequestParam(defaultValue = "0", name = "page") Integer page) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        return ApiResponse.onSuccess(musicalService.getAllMusicals(page, keyword));
    }

    @GetMapping("/{musicalId}")
    @Operation(summary = "뮤지컬 관리 상세", description = "뮤지컬 정보를 조회하는 API")
    public ApiResponse<MusicalResponseDTO.AdminMusicalDetailDTO> getMusicalDetail(@RequestHeader("Authorization") String authorizationHeader,
                                                                                  @PathVariable(name = "musicalId") Long musicalId) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        return ApiResponse.onSuccess(musicalService.getMusicalDetail(musicalId));
    }


}
