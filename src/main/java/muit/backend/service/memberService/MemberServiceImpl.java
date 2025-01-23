package muit.backend.service.memberService;

import lombok.RequiredArgsConstructor;

import muit.backend.converter.MemberConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.EmailLoginAccessTokenResponse;
import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;
import muit.backend.dto.memberDTO.LoginRequestDTO;
import muit.backend.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    // private final BCryptPasswordEncoder encoder;
    //private final TokenProvider tokenProvider;
    //private final TokenProviderMember tokenProviderMember;

    //== 개인회원 가입 - 이메일 ==//
    @Override
    @Transactional
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto) {

        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }
        if (!dto.getPw().equals(dto.getPw_check())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        Member member = MemberConverter.EmailtoMember(dto);
        memberRepository.save(member);

        return MemberConverter.MemberToEmailRegisterResponseDTO(member);

    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("unexpected member"));
    }
}



//    //== 이메일 로그인 == //
//    @Override
//    public EmailLoginAccessTokenResponse EmailLogin(LoginRequestDTO dto){
//
//        String email = dto.getEmail();
//        TokenDTO token = login(dto);
//        Member member = memberRepository.findMemberByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("Member with email " + email + " not found."));
//        EmailLoginAccessTokenResponse result = MemberConverter.TokenRegisterResponseDTO(token, member);
//        return result;
//    }

   /* // 1. 로그인 메서드
    public TokenDTO login(LoginRequestDTO dto) {

        String clientEmail = dto.getEmail();
        String clientPw = dto.getPw();

        // 해당 이메일이 있는지 검증
        Member member = memberRepository.checkEmail(clientEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원이 아닙니다. 회원가입을 해주세요."));

        // 비밀번호 검증
        if (!encoder.matches(clientPw, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        // 사용자 인증에 성공하면 JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(clientEmail, null);
        return tokenProviderMember.generateTokenDto(authentication);
    }

    // == 회원 검증 로직 == //
    @Transactional(readOnly = true)
    public Member getMemberByToken(String token) {
        // 토큰이 유효한지 검증
        if (!tokenProviderMember.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 인증 정보를 추출
        Authentication authentication = tokenProviderMember.getAuthentication(token);

        // 인증 정보에서 사용자 이메일을 가져와 회원 조회
        String email = authentication.getName();
        System.out.println("회원조회 체크 "+email);

        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }*/


