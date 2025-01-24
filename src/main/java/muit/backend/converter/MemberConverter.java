package muit.backend.converter;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;

public class MemberConverter {

    public static Member EmailtoMember(EmailRegisterRequestDTO dto){
        return Member.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .password(dto.getPw())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress()).build();
    }

    public static EmailRegisterResponseDTO MemberToEmailRegisterResponseDTO(Member member){
        return EmailRegisterResponseDTO.builder()
                .id(member.getId())
                .email(member.getEmail()).build();
    }
}