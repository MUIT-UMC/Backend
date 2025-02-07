package muit.backend.service.adminService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.converter.adminConverter.ManageTicketConverter;
import muit.backend.domain.entity.member.MemberTicket;
import muit.backend.domain.enums.ReservationStatus;
import muit.backend.dto.adminDTO.manageTicketDTO.ManageTicketResponseDTO;
import muit.backend.repository.MemberTicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageTicketServiceImpl implements ManageTicketService {

    private final MemberTicketRepository memberTicketRepository;

    @Override
    public Page<ManageTicketResponseDTO.ManageTicketResultListDTO> getAllTickets(Pageable pageable, String keyword, Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 그냥 빈 검색어도 없다고 침

        Page<MemberTicket> memberTickets;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            memberTickets = memberTicketRepository.findByKeyword(pageable, keyword);
            if (memberTickets.isEmpty()) {
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 소극장 공연 정보 조회
            memberTickets = memberTicketRepository.findAllMemberTickets(pageable);
        }

        return memberTickets.map(memberTicket ->
                ManageTicketConverter.toManageTicketResultListDTO(memberTicket, selectedFields, isKeywordSearch)
        );
    }

    @Override
    public ManageTicketResponseDTO.ManageTicketResultDTO getTicket(Long memberTicketId) {
        MemberTicket memberTicket = memberTicketRepository.findById(memberTicketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_TICKET_NOT_FOUND));

        return ManageTicketConverter.toManageTicketResultDTO(memberTicket);
    }

    @Transactional
    @Override
    public ManageTicketResponseDTO.ManageTicketResultDTO updateTicket(Long memberTicketId, @NotNull ReservationStatus reservationStatus) {

        MemberTicket memberTicket = memberTicketRepository.findById(memberTicketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_TICKET_NOT_FOUND));

        memberTicket.updateMemberTicket(reservationStatus);

        return ManageTicketConverter.toManageTicketResultDTO(memberTicket);
    }
}
