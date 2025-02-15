package muit.backend.service;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.*;
import muit.backend.dto.musicalDTO.MusicalResponseDTO;

import java.util.List;

public interface MemberService {
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto);
    public Member findById(Long id);
    public LoginAccessTokenResponse EmailLogin(LoginRequestDTO dto);
    public Member getMemberByToken(String token);
    public Member getAdminByToken(String receivedBearerToken);
    public MyPageResponseDTO getMyPage(Long tokenId, Long memberId);

    public MyPageResponseDTO deactivateMember(Long tokenId, Long memberId);

    public boolean CheckPassword(Member member,PasswordCheckRequestDTO dto);

    public List<MusicalResponseDTO.MusicalHomeDTO> getLikeMusicals(Member member);

}
