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

    public boolean CheckPassword(Member member, PasswordRequestDTO dto);

    public MyPageResponseDTO changePhoneNumber(Long tokenId, Long memberId, PhoneChangeRequestDTO dto);
    public MyPageResponseDTO changeUsername(Long tokenId, Long memberId, UserNameChangeRequestDTO dto);
    public MyPageResponseDTO changeEmail(Long tokenId, Long memberId, EmailVerifyRequestDTO dto);
    public MyPageResponseDTO changePassword(Long tokenId, Long memberId, PasswordChangeRequestDTO dto);
    public MyPageResponseDTO changeAddress(Long tokenId, Long memberId, AddressChangeRequestDTO dto);


    public List<MusicalResponseDTO.MusicalHomeDTO> getLikeMusicals(Member member);

}
