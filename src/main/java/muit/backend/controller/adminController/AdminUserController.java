package muit.backend.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.enums.Role;
import muit.backend.dto.memberDTO.LoginAccessTokenResponse;
import muit.backend.dto.memberDTO.LoginRequestDTO;
import muit.backend.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민(관리자) 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인 api", description = "이메일로 관리자를 로그인")
    // JWT 토큰을 생성하여 반환
    public ApiResponse<LoginAccessTokenResponse> login(@RequestBody LoginRequestDTO dto) {
        try {
            LoginAccessTokenResponse result = memberService.EmailLogin(dto);
            if (!result.getRole().equals(Role.ADMIN)) {
                return ApiResponse.onFailure("403", "관리자 권한이 없습니다.", null);
            }
            return ApiResponse.onSuccess(result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("400", e.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", e.getMessage(),null);
        }
    }
}
