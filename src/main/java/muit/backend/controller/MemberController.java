package muit.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.*;
import muit.backend.service.MemberService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "회원")
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
    @Operation(summary = "로그인 api", description = "이메일로 로그인을 하는 기능.")
    // JWT 토큰을 생성하여 반환
    public ApiResponse<LoginAccessTokenResponse> login(@RequestBody LoginRequestDTO dto) {
        try {
            //TokenDto tokenDto = memberService.login(dto);
            LoginAccessTokenResponse result = memberService.EmailLogin(dto);
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

    @PatchMapping("/{memberId}/deActive")
    @Operation(summary = "회원 탈퇴(비활성화) api", description = "회원 비활성화 하는 기능입니다.")
    public ApiResponse<MyPageResponseDTO> deactivateMember(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("memberId") Long memberId) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.deactivateMember(member.getId(), memberId);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }

    @PatchMapping("{memberId}/changePhone")
    @Operation(summary = "회원 정보 수정 - 핸드폰")
    public ApiResponse<MyPageResponseDTO> changePhone(@RequestHeader("Authorization") String authorizationHeader,
                                                      @PathVariable("memberId") Long memberId,
                                                      @RequestBody PhoneChangeRequestDTO dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.changePhoneNumber(member.getId(), memberId, dto);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }

    @PatchMapping("{memberId}/changeUsername")
    @Operation(summary = "회원 정보 수정 - 아이디")
    public ApiResponse<MyPageResponseDTO> changeUsername(@RequestHeader("Authorization") String authorizationHeader,
                                                      @PathVariable("memberId") Long memberId,
                                                      @RequestBody UserNameChangeRequestDTO dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.changeUsername(member.getId(), memberId, dto);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }

    @PatchMapping("{memberId}/changeEmail")
    @Operation(summary = "회원 정보 수정 - 이메일", description = "이메일로 회원을 구분 하기 때문에, 반드시 재로그인을 해야합니다. redirect 를 로그인 페이지로 해야합니다.")
    public ApiResponse<MyPageResponseDTO> changeEmail(@RequestHeader("Authorization") String authorizationHeader,
                                                         @PathVariable("memberId") Long memberId,
                                                         @RequestBody EmailVerifyRequestDTO dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.changeEmail(member.getId(), memberId, dto);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }

    @PatchMapping("{memberId}/changePassword")
    @Operation(summary = "회원 정보 수정 - 비밀번호")
    public ApiResponse<MyPageResponseDTO> changePassword(@RequestHeader("Authorization") String authorizationHeader,
                                                      @PathVariable("memberId") Long memberId,
                                                      @RequestBody PasswordChangeRequestDTO dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        MyPageResponseDTO myPageResponseDTO = memberService.changePassword(member.getId(), memberId, dto);
        return ApiResponse.onSuccess(myPageResponseDTO);
    }

    @PostMapping("{memberId}/checkPassword")
    @Operation(summary = "회원 정보 변경 전 + 소극장 등록 전 비밀 번호 확인하는 api")
    public ApiResponse<Boolean> checkPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PasswordRequestDTO dto) {
        Member member = memberService.getMemberByToken(authorizationHeader);
        boolean isValid = memberService.CheckPassword(member, dto);
        if (!isValid) {
            return ApiResponse.onFailure("400", "비밀번호가 일치하지 않습니다.", false);
        }
        return ApiResponse.onSuccess(true);
    }






}
