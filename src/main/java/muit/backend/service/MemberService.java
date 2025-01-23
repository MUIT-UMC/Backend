package muit.backend.service;

import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;

public interface MemberService {
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto);

}
