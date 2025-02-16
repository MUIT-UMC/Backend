package muit.backend.converter;

import muit.backend.config.jwt.TokenDTO;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.LoginAccessTokenResponse;
import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;

public class MemberConverter {

    public static Member EmailtoMember(EmailRegisterRequestDTO dto, String encodedPassword){
        return Member.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .password(encodedPassword)
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .oauthProvider("MUIT")
                .address(dto.getAddress()).build();
    }

    public static EmailRegisterResponseDTO MemberToEmailRegisterResponseDTO(Member member){
        return EmailRegisterResponseDTO.builder()
                .id(member.getId())
                .email(member.getEmail()).build();
    }

    public static LoginAccessTokenResponse TokenLoginResponseDTO(TokenDTO dto, Member member){
        return LoginAccessTokenResponse.builder()
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .id(member.getId())
                .name(member.getName())
                .username(member.getUsername())
                .role(member.getRole())
                .build();
    }
}