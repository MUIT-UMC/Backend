package muit.backend.service.memberService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.*;

public interface MemberService {
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto);
    public Member findById(Long id);
    public EmailLoginAccessTokenResponse EmailLogin(LoginRequestDTO dto);
    public Member getMemberByToken(String token);
    public MyPageResponseDTO getMyPage(Long tokenId, Long memberId);

}
