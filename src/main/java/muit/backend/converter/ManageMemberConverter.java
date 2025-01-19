package muit.backend.converter;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.manageMemberDTO.ManageMemberResponseDTO;

import java.util.Set;

public class ManageMemberConverter {

    public static ManageMemberResponseDTO.ManageMemberResultListDTO toMangeMemberResultListDTO(Member member, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            return ManageMemberResponseDTO.ManageMemberResultListDTO.builder()
                    .memberId(member.getId())
                    .username(member.getUsername())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .gender(member.getGender())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return ManageMemberResponseDTO.ManageMemberResultListDTO.builder()
                .memberId(member.getId())  // ID는 항상 포함
                .username(selectedFields.contains("username") ? member.getUsername() : null)
                .name(selectedFields.contains("name") ? member.getName() : null)
                .email(selectedFields.contains("email") ? member.getEmail() : null)
                .phone(selectedFields.contains("phone") ? member.getPhone() : null)
                .gender(selectedFields.contains("gender") ? member.getGender() : null)
                .build();
    }

    public static ManageMemberResponseDTO.ManageMemberResultDTO toManageMemberResultDTO(Member member) {
        return ManageMemberResponseDTO.ManageMemberResultDTO.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .birthDate(member.getBirthDate())
                .gender(member.getGender())
                .address(member.getAddress())
                .build();
    }
}