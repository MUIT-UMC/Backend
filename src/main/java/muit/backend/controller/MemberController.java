package muit.backend.controller;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.*;
import muit.backend.service.memberService.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
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

    @PostMapping("/email/login")  // JWT 토큰을 생성하여 반환
    public ApiResponse<EmailLoginAccessTokenResponse> login(@RequestBody LoginRequestDTO dto) {
        try {
            //TokenDto tokenDto = memberService.login(dto);
            EmailLoginAccessTokenResponse result = memberService.EmailLogin(dto);
            return ApiResponse.onSuccess(result);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("400", e.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", e.getMessage(),null);
        }
    }

    @GetMapping("/{memberId}")
public ApiResponse<MyPageResponseDTO> myPage(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("memberId") Long memberId) {
        Member member = memberService.getMemberByToken(authorizationHeader);

       MyPageResponseDTO myPageResponseDTO = memberService.getMyPage(member.getId(), memberId);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }




}
