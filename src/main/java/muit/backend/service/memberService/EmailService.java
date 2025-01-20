package muit.backend.service.memberService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import muit.backend.repository.MemberRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    private final Map<String, String> verificationCodes = new HashMap<>();

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

        // 이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + code);

        // 이메일 전송
        javaMailSender.send(message);

        // redis 설정은 나중에 하겠습니다..
        verificationCodes.put(email, code);
    }

    // 인증 번호 검증 메서드
    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        System.out.println("stored code is " + storedCode);
        System.out.println("input code is " + code);
        System.out.println("비교 결과 " + storedCode.equals(code));
        return (storedCode != null && storedCode.equals(code));
    }






}