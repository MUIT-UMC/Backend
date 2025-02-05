package muit.backend.service.memberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muit.backend.config.RedisUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    private static final Long EMAIL_VERIFICATION_CODE_VALID_TIME = 1000L * 60 * 5; // 5분

    //랜덤 인증번호 생성 메서드
    private String createCode() {
        Random random = new Random();
        int code = random.nextInt(999999); // 6자리 숫자 생성
        return String.format("%06d", code);
    }

    // 이메일 폼 생성
//    public SimpleMailMessage createEmailForm(String toEmail) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        String code = "";
//        message.setTo(toEmail);
//        message.setSubject("뮤잇 인증번호 발송");
//        message.setText(code);
//
//        return message;
//    }

    // 이메일 발송하기
    @Async
    public void sendEmail(String email) {
        // 인증번호 생성
        String code = createCode();
        System.out.println("Generated verification code for email: " + email);

        // 이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + code);

        // 이메일 전송
        javaMailSender.send(message);

        // redis에 인증번호 저장
        String redisKey = "EmailVerificationCode:" + email;
        redisUtil.setDataExpire(redisKey, code, EMAIL_VERIFICATION_CODE_VALID_TIME);
        System.out.println("Verification code saved in Redis for email: " + email);
    }

    // 인증 번호 검증 메서드
    public boolean verifyCode(String email, String code) {

        String redisKey = "EmailVerificationCode:" + email;
        String storedCode = redisUtil.getData(redisKey);

        // 저장된 코드가 없으면
        if (storedCode == null) {
            System.out.println("No verification code found for email: " + email);
            return false;
        }

        // 코드 일치 여부
        boolean verified = storedCode.equals(code);

        // 검증 성공하면 Redis에서 코드 삭제
        if (verified) {
            redisUtil.deleteData(redisKey);
            System.out.println("Email verification code successful & deleted from Redis for email: " + email);
        } else {
            System.out.println("Email verification code failed for email: " + email);
        }

        return verified;
    }
}