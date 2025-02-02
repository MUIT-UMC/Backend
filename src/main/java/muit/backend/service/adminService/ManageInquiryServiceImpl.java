package muit.backend.service.adminService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.adminConverter.ManageInquiryConverter;
import muit.backend.domain.entity.member.Inquiry;
import muit.backend.dto.adminDTO.manageInquiryDTO.ManageInquiryResponseDTO;
import muit.backend.repository.InquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageInquiryServiceImpl implements ManageInquiryService {

    private final InquiryRepository inquiryRepository;

    // 문의 내역 전체 조회
    @Override
    public Page<ManageInquiryResponseDTO.ManageInquiryResultListDTO> getAllInquiries(Pageable pageable, String keyword, Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 그냥 빈 검색어도 없다고 침

        Page<Inquiry> inquiries;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            inquiries = inquiryRepository.findByKeyword(pageable, keyword);
            if (inquiries.isEmpty()) {
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 소극장 공연 정보 조회
            inquiries = inquiryRepository.findAllWithMember(pageable);
        }

        return inquiries.map(inquiry ->
                ManageInquiryConverter.toManageInquiryResultListDTO(inquiry, selectedFields, isKeywordSearch)
        );
    }

    // 특정 문의 상세 조회
    @Override
    public ManageInquiryResponseDTO.ManageInquiryResultDTO getInquiry(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findByIdWithMemberAndResponse(inquiryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.INQUIRY_NOT_FOUND));

        return ManageInquiryConverter.toManageInquiryResultDTO(inquiry);
    }

}
