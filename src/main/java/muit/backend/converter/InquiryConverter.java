package muit.backend.converter;

import muit.backend.domain.entity.member.Inquiry;
import muit.backend.domain.entity.member.InquiryResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.InquiryStatus;
import muit.backend.dto.inquiryDTO.InquiryRequestDTO;
import muit.backend.dto.inquiryDTO.InquiryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class InquiryConverter {

    public static Inquiry toInquiry (InquiryRequestDTO.InquiryCreateRequestDTO requestDTO, Member member) {
        return Inquiry.builder()
                .inquiryStatus(InquiryStatus.AWAIT)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .member(member)
                .build();
    }

    public static InquiryResponseDTO.GeneralInquiryResultDTO toInquiryGeneralDTO (Inquiry inquiry) {
        InquiryResponseDTO.GeneralInquiryResponseResultDTO responseDTO = null;

        if(inquiry.getInquiryResponse()!=null){
            responseDTO = InquiryConverter.toInquiryResponseGeneralDTO(inquiry.getInquiryResponse());

        }
        return InquiryResponseDTO.GeneralInquiryResultDTO.builder()
                .content(inquiry.getContent())
                .title(inquiry.getTitle())
                .createdAt(inquiry.getCreatedAt())
                .id(inquiry.getId())
                .MemberId(inquiry.getMember().getId())
                .inquiryResponse(responseDTO)
                .status(inquiry.getInquiryStatus())
                .build();
    }

    public static InquiryResponseDTO.GeneralInquiryResponseResultDTO toInquiryResponseGeneralDTO (InquiryResponse inquiryResponse) {
        return InquiryResponseDTO.GeneralInquiryResponseResultDTO.builder()
                .content(inquiryResponse.getContent())
                .id(inquiryResponse.getId())
                .createdAt(inquiryResponse.getCreatedAt())
                .MemberId(inquiryResponse.getMember().getId())
                .build();
    }

    public static InquiryResponse toInquiryResponse (InquiryRequestDTO.InquiryResponseRequestDTO requestDTO, Inquiry inquiry, Member member) {
        return InquiryResponse.builder()
                .content(requestDTO.getContent())
                .member(member)
                .inquiry(inquiry)
                .build();
    }

    public static InquiryResponseDTO.InquiryResultListDTO toInquiryListDTO (Page<Inquiry> inquiryPage) {

        List<InquiryResponseDTO.GeneralInquiryResultDTO> inquiryDTOList = inquiryPage.stream().map(InquiryConverter::toInquiryGeneralDTO).toList();

        return InquiryResponseDTO.InquiryResultListDTO.builder()
                .inquiries(inquiryDTOList)
                .listSize(inquiryDTOList.size())
                .totalPage(inquiryPage.getTotalPages())
                .totalElements(inquiryPage.getTotalElements())
                .isLast(inquiryPage.isLast())
                .isFirst(inquiryPage.isFirst())
                .build();
    }


}
