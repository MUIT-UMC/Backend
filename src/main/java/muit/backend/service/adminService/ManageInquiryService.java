package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.manageInquiryDTO.ManageInquiryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageInquiryService {
    public Page<ManageInquiryResponseDTO.ManageInquiryResultListDTO> getAllInquiries(Pageable pageable, String keyword, Set<String> selectedFields);

    public ManageInquiryResponseDTO.ManageInquiryResultDTO getInquiry(Long inquiryId);


}