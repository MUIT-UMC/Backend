package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.member.Inquiry;
import muit.backend.domain.entity.member.InquiryResponse;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseResponseDTO;

public class InquiryResponseConverter {

    public static InquiryResponseResponseDTO.InquiryResponseUpsertDTO toInquiryResponseUpsertDTO(InquiryResponse inquiryResponse) {
        return InquiryResponseResponseDTO.InquiryResponseUpsertDTO.builder()
                .inquiryId(inquiryResponse.getInquiry().getId())
                .responseId(inquiryResponse.getId())
                .content(inquiryResponse.getContent())
                .createdAt(inquiryResponse.getCreatedAt())
                .build();

    }

    public static InquiryResponse toInquiryResponse(Inquiry inquiry, InquiryResponseRequestDTO.InquiryResponseUpsertDTO requestDTO) {
        return InquiryResponse.builder()
                .inquiry(inquiry)
                .content(requestDTO.getContent())
                .build();
    }
}
