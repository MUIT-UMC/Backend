package muit.backend.service.memberService.socialLogin;

import lombok.RequiredArgsConstructor;
import muit.backend.config.jwt.TokenDTO;
import muit.backend.config.jwt.TokenProvider;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.Role;
import muit.backend.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOauthService {
    private final GoogleClient googleClient;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public AuthResponse authenticateWithGoogle(String code) {
        GoogleAccountProfileResponse googleUser = googleClient.getGoogleAccountProfile(code);
        Member member = memberRepository.findMemberByEmail(googleUser.getEmail())
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(googleUser.getEmail())
                            .username(googleUser.getEmail())
                            .name(googleUser.getName())
                            .role(Role.USER)
                            .password("") // OAuth 사용자는 비밀번호가 없음
                            .build();
                    return memberRepository.save(newMember);
                });
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getEmail(), null);
        TokenDTO token = tokenProvider.generateTokenDto(authentication);

        return new AuthResponse(token.getAccessToken(), token.getRefreshToken());
        //return googleClient.getGoogleAccountProfile(code);
    }


}
