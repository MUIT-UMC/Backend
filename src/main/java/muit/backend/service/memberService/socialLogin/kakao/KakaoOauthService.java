package muit.backend.service.memberService.socialLogin.kakao;

import lombok.RequiredArgsConstructor;
import muit.backend.config.jwt.TokenDTO;
import muit.backend.config.jwt.TokenProvider;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.LoginType;
import muit.backend.domain.enums.Role;
import muit.backend.repository.MemberRepository;
import muit.backend.service.memberService.socialLogin.AuthResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOauthService {

    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public AuthResponse authenticateWithKakao(String code) {

        // 1. 액세스 토큰 요청
        String accessToken = kakaoClient.requestAccessToken(code);
        // 2. 프로필 정보 요청
        KakaoAccountProfileResponse kakaoUser = kakaoClient.requestProfile(accessToken);

        Member member = memberRepository.findMemberByEmail(kakaoUser.getKakaoAccount().getEmail())
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(kakaoUser.getKakaoAccount().getEmail())
                            .username(kakaoUser.getKakaoAccount().getEmail())
                            .name(kakaoUser.getKakaoAccount().getProfile().getNickname())
                            .role(Role.USER)
                            .password("")
                            .loginType(LoginType.KAKAO)
                            .oauthProvider("MUIT")
                            .build();
                    return memberRepository.save(newMember);
                });

        // 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), null);
        TokenDTO token = tokenProvider.generateTokenDto(authentication);

        return new AuthResponse(token.getAccessToken(), token.getRefreshToken());
    }
}