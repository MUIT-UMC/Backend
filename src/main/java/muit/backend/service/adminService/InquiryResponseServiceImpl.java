package muit.backend.service.adminService;

import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.adminConverter.InquiryResponseConverter;
import muit.backend.domain.entity.member.Inquiry;
import muit.backend.domain.entity.member.InquiryResponse;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseRequestDTO;
import muit.backend.dto.adminDTO.inquiryResponseDTO.InquiryResponseResponseDTO;
import muit.backend.repository.InquiryRepository;
import muit.backend.repository.InquiryResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryResponseServiceImpl implements InquiryResponseService {

    private final InquiryRepository inquiryRepository;
    private final InquiryResponseRepository inquiryResponseRepository;

    // 답변 생성
    @Override
    @Transactional
    public InquiryResponseResponseDTO.InquiryResponseUpsertDTO upsertResponse(Member member, Long inquiryId, InquiryResponseRequestDTO.InquiryResponseUpsertDTO requestDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.INQUIRY_NOT_FOUND));

        InquiryResponse inquiryResponse;

        if (inquiry.getInquiryResponse() != null) {
            // 기존 답변이 있으면 수정하기
            inquiryResponse = inquiry.getInquiryResponse();
            inquiryResponse.updateContent(requestDTO.getContent(),member);
        } else {
            // 답변이 없으면 생성하기
            inquiryResponse = InquiryResponseConverter.toInquiryResponse(member, inquiry, requestDTO);
            inquiryResponse = inquiryResponseRepository.save(inquiryResponse);

            inquiry.addResponse(inquiryResponse);  // 답변 추가됨 -> 상태 변경 호출
        }

        return InquiryResponseConverter.toInquiryResponseUpsertDTO(inquiryResponse);
    }
}