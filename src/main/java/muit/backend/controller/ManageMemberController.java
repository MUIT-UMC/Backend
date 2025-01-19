package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.dto.manageMemberDTO.ManageMemberResponseDTO;
import muit.backend.service.ManageMemberService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "어드민이 사용자 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class ManageMemberController {
    private final ManageMemberService manageMemberService;

    @Operation(summary = "전체 사용자 조회")
    @GetMapping
    public ApiResponse<Page<ManageMemberResponseDTO.ManageMemberResultListDTO>> getAllMembers(@ParameterObject Pageable pageable,
                                                                                              @RequestParam(required = false) String keyword, @RequestParam(required = false) Set<String> selectedFields) {
        Page<ManageMemberResponseDTO.ManageMemberResultListDTO> members =
                manageMemberService.getAllMembers(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(members);
    }
}
