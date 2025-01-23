/*
package muit.backend.config.jwt;

import lombok.RequiredArgsConstructor;
import muit.backend.domain.entity.member.Member;
import muit.backend.service.memberService.MemberService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalStateException("Invalid refresh token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.findById(memberId);
        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}
*/
