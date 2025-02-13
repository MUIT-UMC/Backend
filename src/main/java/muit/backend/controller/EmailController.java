package muit.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.ApiResponse;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.EmailVerifyRequestDTO;
import muit.backend.repository.MemberRepository;
import muit.backend.service.memberService.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "이메일")
public class EmailController {
    private final EmailService emailService;
    private final MemberRepository memberRepository;

    // 인증코드 메일 발송
    @PostMapping("/sendCode")
    public ApiResponse<String> mailSend(@RequestBody EmailVerifyRequestDTO dto) throws MessagingException {
        try {
            String email = dto.getEmail();
            //소셜 로그인 고려해야함
            Optional<Member> member = memberRepository.findMemberByEmail(email);
            if (member.isPresent()) {
                throw new GeneralException(ErrorStatus.EMAIL_ALREADY_EXIST);
            } else emailService.sendEmail(email);
            return ApiResponse.onSuccess("Verification code sent to " + email);
        } catch (IllegalStateException e) {
            return ApiResponse.onFailure("400", e.getMessage(), "다른 이메일로 가입해주세요");
        }
    }

    // 인증코드 인증
    @PostMapping("/verify")
    public ApiResponse<String> verify(@RequestParam String email, @RequestParam String code) {
        log.info("EmailController.verify()");
        emailService.verifyCode(email, code);
        return ApiResponse.onSuccess("인증이 완료되었습니다.");
    }
}