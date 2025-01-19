package muit.backend.service;


import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.ManageMemberConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.manageMemberDTO.ManageMemberResponseDTO;
import muit.backend.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static muit.backend.apiPayLoad.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageMemberServiceImpl implements ManageMemberService {

    private final MemberRepository memberRepository;

    // 사용자 전체 조회
    @Override
    public Page<ManageMemberResponseDTO.ManageMemberResultListDTO> getAllMembers(
            Pageable pageable,
            String keyword,
            Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty();
        Page<Member> members;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            members = memberRepository.findByKeyword(pageable, keyword);
            if (members.isEmpty()) { // 검색 결과 없으면 빈 페이지
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 사용자 정보 조회
            members = memberRepository.findAll(pageable);
        }

        return members.map(member ->
                ManageMemberConverter.toMangeMemberResultListDTO(member, selectedFields, isKeywordSearch));
    }

    // 특정 사용자 조회 (단건 조회)
    @Override
    public ManageMemberResponseDTO.ManageMemberResultDTO getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        return ManageMemberConverter.toManageMemberResultDTO(member);
    }
}