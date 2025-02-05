package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.*;
import muit.backend.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입 api", description = "이메일로 회원 가입 하는 기능.")
    public ApiResponse<EmailRegisterResponseDTO> emailRegister(@RequestBody EmailRegisterRequestDTO dto) {
        try{
            EmailRegisterResponseDTO response = memberService.emailSignUp(dto);
            if (response != null) {
                return ApiResponse.onSuccess(response);
            } else {
                return ApiResponse.onFailure("400", dto.getEmail() + "은 이미 회원입니다. 다른 이메일로 가입해주세요", null);
            }
        } catch (IllegalStateException e){
            return ApiResponse.onFailure("400", e.getMessage(), null);
        }
    }

    @PostMapping("/email/login")
    // JWT 토큰을 생성하여 반환
    public ApiResponse<EmailLoginAccessTokenResponse> login(@RequestBody LoginRequestDTO dto) {
        try {
            //TokenDto tokenDto = memberService.login(dto);
            EmailLoginAccessTokenResponse result = memberService.EmailLogin(dto);
            return ApiResponse.onSuccess(result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("400", e.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", e.getMessage(),null);
            // 이부분 에러 메시지 추가하겠습니다.
        }
    }

    @GetMapping("/{memberId}")
    @Parameters({
            @Parameter(name = "Authorization", description = "JWT 토큰으로, 사용자의 아이디, request header 입니다!")
    })
    @Operation(summary = "마이 페이지 조회 api", description = "마이 페이지 조회 합니다")
    public ApiResponse<MyPageResponseDTO> myPage(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("memberId") Long memberId) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.getMyPage(member.getId(), memberId);

        return ApiResponse.onSuccess(myPageResponseDTO);
    }




}
