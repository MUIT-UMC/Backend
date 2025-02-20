package muit.backend.service.adminService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface InquiryResponseService {

    public InquiryResponseResponseDTO.InquiryResponseUpsertDTO upsertResponse(Member member, Long inquiryId, InquiryResponseRequestDTO.InquiryResponseUpsertDTO requestDTO);
}
