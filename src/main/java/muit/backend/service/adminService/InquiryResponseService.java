package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface InquiryResponseService {

    public InquiryResponseResponseDTO.InquiryResponseUpsertDTO upsertResponse(Long inquiryId, InquiryResponseRequestDTO.InquiryResponseUpsertDTO requestDTO);
}
