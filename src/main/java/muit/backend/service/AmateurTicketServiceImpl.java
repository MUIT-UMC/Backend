package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.AmateurTicketConverter;
import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.dto.amateurTicketDTO.AmateurTicketResponseDTO;
import muit.backend.repository.AmateurShowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AmateurTicketServiceImpl implements AmateurTicketService {

    private final AmateurShowRepository amateurShowRepository;

    @Override
    public Page<AmateurTicketResponseDTO.ResultListDTO> getAllTickets(Pageable pageable,
                                                                      String keyword,
                                                                      Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 그냥 빈 검색어도 없다고 침

        Page<AmateurShow> amateurShows;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            amateurShows = amateurShowRepository.findTicketsByKeyword(pageable, keyword);
            if(amateurShows.isEmpty()) {
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 소극장 공연 정보 조회
            amateurShows = amateurShowRepository.findAll(pageable);
        }

        return amateurShows.map(amateurShow ->
                AmateurTicketConverter.toResultListDTO(amateurShow, selectedFields, isKeywordSearch)
        );
    }
}
