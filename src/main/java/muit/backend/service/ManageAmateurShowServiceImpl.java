package muit.backend.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import muit.backend.converter.ManageAmateurShowConverter;
import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.domain.enums.AmateurStatus;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowRequestDTO;
import muit.backend.dto.manageAmateurShowDTO.ManageAmateurShowResponseDTO;
import muit.backend.repository.AmateurShowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageAmateurShowServiceImpl implements ManageAmateurShowService {

    private final AmateurShowRepository amateurShowRepository;

    // 소극장 공연 전체 조회
    @Override
    public Page<ManageAmateurShowResponseDTO.ResultListDTO> getAllAmateurShows(Pageable pageable, String keyword, Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 그냥 빈 검색어도 없다고 침

        Page<AmateurShow> amateurShows;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            amateurShows = amateurShowRepository.findByKeyword(pageable, keyword);
            if(amateurShows.isEmpty()) {
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 소극장 공연 정보 조회
            amateurShows = amateurShowRepository.findAllWithMember(pageable);
        }

        return amateurShows.map(amateurShow ->
                ManageAmateurShowConverter.toResultListDTO(amateurShow, selectedFields, isKeywordSearch)
        );
    }

    // 특정 소극장 공연 조회
    @Override
    public ManageAmateurShowResponseDTO.ResultDTO getAmateurShow(Long amateurShowId) {
        AmateurShow amateurShow = amateurShowRepository.findByIdWithMemberAndSummary(amateurShowId)
                .orElseThrow(() -> new RuntimeException("AmateurShow not found"));

        return ManageAmateurShowConverter.toResultDTO(amateurShow);
    }

    // 특정 소극장 공연 정보 수정
    @Transactional
    @Override
    public ManageAmateurShowResponseDTO.ResultDTO updateAmateurShow(Long amateurShowId, ManageAmateurShowRequestDTO.UpdateDTO requestDTO) {
        AmateurShow amateurShow = amateurShowRepository.findById(amateurShowId)
                .orElseThrow(() -> new RuntimeException("amateurShow not found"));

        amateurShow.updateAmateurShow(requestDTO);

        return ManageAmateurShowConverter.toResultDTO(amateurShow);
    }

    // 소극장 공연 최종 등록/반려
    @Transactional
    @Override
    public ManageAmateurShowResponseDTO.DecideDTO decideAmateurShow(Long amateurShowId, @NotNull AmateurStatus amateurStatus, ManageAmateurShowRequestDTO.DecideDTO requestDTO) {
        AmateurShow amateurShow = amateurShowRepository.findById(amateurShowId)
                .orElseThrow(() -> new RuntimeException("amateurShow not found"));

        amateurShow.decideAmateurShow(amateurStatus, requestDTO);
        return ManageAmateurShowConverter.toDecideDTO(amateurShow);
    }
}
