package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.manageMemberDTO.ManageMemberRequestDTO;
import muit.backend.dto.adminDTO.manageMemberDTO.ManageMemberResponseDTO;
import muit.backend.service.MemberService;
import muit.backend.service.adminService.ManageMemberService;
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
    private final MemberService memberService;

    @Operation(summary = "전체 사용자 조회")
    @GetMapping
    public ApiResponse<Page<ManageMemberResponseDTO.ManageMemberResultListDTO>> getAllMembers(@RequestHeader("Authorization") String authorizationHeader,
                                                                                              @ParameterObject Pageable pageable,
                                                                                              @RequestParam(required = false) String keyword,
                                                                                              @RequestParam(required = false) Set<String> selectedFields) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        Page<ManageMemberResponseDTO.ManageMemberResultListDTO> members = manageMemberService.getAllMembers(pageable, keyword, selectedFields);
        return ApiResponse.onSuccess(members);
    }

    @Operation(summary = "특정 사용자 조회 (단건 조회)")
    @GetMapping("/{memberId}")
    public ApiResponse<ManageMemberResponseDTO.ManageMemberResultDTO> getMember(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @PathVariable("memberId") Long memberId) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        ManageMemberResponseDTO.ManageMemberResultDTO member = manageMemberService.getMember(memberId);
        return ApiResponse.onSuccess(member);
    }

    @Operation(summary = "특정 사용자 정보 수정")
    @PatchMapping("/{memberId}/update")
    public ApiResponse<ManageMemberResponseDTO.ManageMemberResultDTO> updateMember(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @PathVariable("memberId") Long memberId,
                                                                                   @RequestBody ManageMemberRequestDTO.UpdateMemberRequestDTO requestDTO) {
        Member admin = memberService.getAdminByToken(authorizationHeader);

        ManageMemberResponseDTO.ManageMemberResultDTO updateMember = manageMemberService.updateMember(memberId, requestDTO);
        return ApiResponse.onSuccess(updateMember);
    }
}
