package muit.backend.service;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.InquiryStatus;
import muit.backend.dto.inquiryDTO.InquiryRequestDTO;
import muit.backend.dto.inquiryDTO.InquiryResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface InquiryService {
    InquiryResponseDTO.GeneralInquiryResultDTO createInquiry(InquiryRequestDTO.InquiryCreateRequestDTO requestDTO, Member member);

    //문의 리스트 조회
    InquiryResponseDTO.InquiryResultListDTO getList(Member member, Integer page, Integer size);

    InquiryResponseDTO.GeneralInquiryResultDTO getOne(Long inquiryId, Member member);

    InquiryResponseDTO.InquiryDeleteResultDTO deleteInquiry(Long inquiryId, Member member);
}
