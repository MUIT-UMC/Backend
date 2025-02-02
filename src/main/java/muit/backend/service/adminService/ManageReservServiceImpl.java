/*
package muit.backend.service.adminService;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.adminConverter.ManageReservConverter;
import muit.backend.domain.entity.member.Reservation;
import muit.backend.dto.adminDTO.manageReservDTO.ManageReservResponseDTO;
import muit.backend.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageReservServiceImpl implements ManageReservService {

    private final ReservationRepository reservationRepository;

    @Override
    public Page<ManageReservResponseDTO.ManageReservResultListDTO> getAllReservations(Pageable pageable, String keyword, Set<String> selectedFields) {

        // 검색어가 있는지 확인
        boolean isKeywordSearch = keyword != null && !keyword.trim().isEmpty(); // 그냥 빈 검색어도 없다고 침

        Page<Reservation> reservations;

        // 검색어가 있으면 해당 키워드로 검색
        if (isKeywordSearch) {
            reservations = reservationRepository.findByKeyword(pageable, keyword);
            if (reservations.isEmpty()) {
                return Page.empty(pageable);
            }
        } else { // 검색어가 없으면 모든 소극장 공연 정보 조회
            reservations = reservationRepository.findAll(pageable);
        }

        return reservations.map(reservation ->
                ManageReservConverter.toManageReservResultListDTO(reservation, selectedFields, isKeywordSearch)
        );
    }
}
*/
