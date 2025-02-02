package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.member.Inquiry;
import muit.backend.dto.adminDTO.manageInquiryDTO.ManageInquiryResponseDTO;

import java.util.Set;

public class ManageInquiryConverter {

    public static ManageInquiryResponseDTO.ManageInquiryResultListDTO toManageInquiryResultListDTO(Inquiry inquiry, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            return ManageInquiryResponseDTO.ManageInquiryResultListDTO.builder()
                    .inquiryId(inquiry.getId())
                    .memberId(inquiry.getMember().getId())
                    .userName(inquiry.getMember().getUsername())
                    .memberName(inquiry.getMember().getName())
                    .email(inquiry.getMember().getEmail())
                    .phone(inquiry.getMember().getPhone())
                    .createdAt(inquiry.getCreatedAt())
                    .inquiryStatus(inquiry.getInquiryStatus())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return ManageInquiryResponseDTO.ManageInquiryResultListDTO.builder()
                .inquiryId(inquiry.getId())  // ID는 항상 포함
                .userName(selectedFields.contains("userName") ? inquiry.getMember().getUsername() : null)
                .memberName(selectedFields.contains("memberName") ? inquiry.getMember().getName() : null)
                .email(selectedFields.contains("email") ? inquiry.getMember().getEmail() : null)
                .phone(selectedFields.contains("phone") ? inquiry.getMember().getPhone() : null)
                .createdAt(selectedFields.contains("createdAt") ? inquiry.getCreatedAt() : null)
                .inquiryStatus(selectedFields.contains("inquiryStatus") ? inquiry.getInquiryStatus() : null)
                .build();
    }


    public static ManageInquiryResponseDTO.ManageInquiryResultDTO toManageInquiryResultDTO(Inquiry inquiry) {

        // Member 정보
        ManageInquiryResponseDTO.Member memberInfo = ManageInquiryResponseDTO.Member.builder()
                .memberId(inquiry.getMember().getId())
                .memberName(inquiry.getMember().getName())
                .email(inquiry.getMember().getEmail())
                .phone(inquiry.getMember().getPhone())
                .build();

        // Inquiry 정보
        ManageInquiryResponseDTO.Inquiry inquiryInfo = ManageInquiryResponseDTO.Inquiry.builder()
                .inquiryId(inquiry.getId())
                .createdAt(inquiry.getCreatedAt())
                .status(inquiry.getInquiryStatus())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .build();

        // Response 정보
        ManageInquiryResponseDTO.Response responseInfo = new ManageInquiryResponseDTO.Response();

        if (inquiry.getInquiryResponse() != null) { // 답변 있을 때
            responseInfo = ManageInquiryResponseDTO.Response.builder()
                    .responseId(inquiry.getInquiryResponse().getId())
                    .content(inquiry.getInquiryResponse().getContent())
                    .createdAt(inquiry.getInquiryResponse().getCreatedAt())
                    .build();
        }

        return ManageInquiryResponseDTO.ManageInquiryResultDTO.builder()
                .member(memberInfo)
                .inquiry(inquiryInfo)
                .response(responseInfo)
                .build();
    }
}
