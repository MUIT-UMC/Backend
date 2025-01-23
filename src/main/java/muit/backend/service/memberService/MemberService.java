package muit.backend.service.memberService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.EmailLoginAccessTokenResponse;
import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;
import muit.backend.dto.memberDTO.LoginRequestDTO;

public interface MemberService {
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto);
    public Member findById(Long id);
    //public EmailLoginAccessTokenResponse EmailLogin(LoginRequestDTO dto);

}
