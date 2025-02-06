package muit.backend.service;


import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.InquiryConverter;
import muit.backend.domain.entity.member.Inquiry;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.InquiryStatus;
import muit.backend.domain.enums.Role;
import muit.backend.dto.inquiryDTO.InquiryRequestDTO;
import muit.backend.dto.inquiryDTO.InquiryResponseDTO;
import muit.backend.repository.InquiryRepository;
import muit.backend.repository.InquiryResponseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryResponseRepository inquiryResponseRepository;

    //문의 생성
    @Override
    @Transactional
    public InquiryResponseDTO.GeneralInquiryResultDTO createInquiry(InquiryRequestDTO.InquiryCreateRequestDTO requestDTO, Member member) {
        Inquiry inquiry = InquiryConverter.toInquiry(requestDTO, member);
        inquiryRepository.save(inquiry);

        return InquiryConverter.toInquiryGeneralDTO(inquiry);
    }

    //문의 리스트 조회
    @Override
    public InquiryResponseDTO.InquiryResultListDTO getList(Member member, InquiryStatus status, Integer page, Integer size) {
        Page<Inquiry> inquiryPage = inquiryRepository.findAllByMember(member, PageRequest.of(page,size));

        return InquiryConverter.toInquiryListDTO(inquiryPage);
    }

    //문의 단건 조회
    @Override
    public InquiryResponseDTO.GeneralInquiryResultDTO getOne(Long inquiryId, Member member) {
        Inquiry inquiry = inquiryRepository.findByIdWithMemberAndResponse(inquiryId).orElseThrow(()->new GeneralException(ErrorStatus.INQUIRY_NOT_FOUND));

        return InquiryConverter.toInquiryGeneralDTO(inquiry);
    }

    @Override
    @Transactional
    public InquiryResponseDTO.InquiryDeleteResultDTO deleteInquiry(Long inquiryId, Member member) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(()->new GeneralException(ErrorStatus.INQUIRY_NOT_FOUND));
        if(inquiry.getInquiryResponse()!=null){
            return InquiryResponseDTO.InquiryDeleteResultDTO.builder().message("답변이 등록된 문의는 삭제할 수 없습니다.").build();
        }
        inquiryRepository.delete(inquiry);

        return InquiryResponseDTO.InquiryDeleteResultDTO.builder().message("문의가 삭제되었습니다.").build();
    }
}
