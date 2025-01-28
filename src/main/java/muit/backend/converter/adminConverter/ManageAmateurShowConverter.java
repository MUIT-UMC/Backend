package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.amateur.AmateurShow;
import muit.backend.dto.adminDTO.manageAmateurShowDTO.ManageAmateurShowResponseDTO;

import java.util.Set;

public class ManageAmateurShowConverter {

    public static ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO toManageAmateurShowResultListDTO(AmateurShow amateurShow, Set<String> selectedFields, boolean isKeywordSearch) {

        // 키워드 검색이거나 전체 조회인 경우 모든 필드 포함
        if (isKeywordSearch || selectedFields == null || selectedFields.isEmpty()) {
            return ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO.builder()
                    .amateurShowId(amateurShow.getId())
                    .amateurShowName(amateurShow.getName())
                    .schedule(amateurShow.getSchedule())
                    .memberName(amateurShow.getMember().getName())
                    .amateurStatus(amateurShow.getAmateurStatus())
                    .build();
        }

        // selectedFields로 특정 필드만 선택한 경우
        return ManageAmateurShowResponseDTO.ManageAmateurShowResultListDTO.builder()
                .amateurShowId(amateurShow.getId())  // ID는 항상 포함
                .amateurShowName(selectedFields.contains("amateurShowName") ? amateurShow.getName() : null)
                .schedule(selectedFields.contains("schedule") ? amateurShow.getSchedule() : null)
                .memberName(selectedFields.contains("memberName") ? amateurShow.getMember().getName() : null)
                .build();
    }

    public static ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO toManageAmateurShowResultDTO(AmateurShow amateurShow) {
        return ManageAmateurShowResponseDTO.ManageAmateurShowResultDTO.builder()
                .amateurShowId(amateurShow.getId())
                .amateurShowName(amateurShow.getName())
                .memberName(amateurShow.getMember().getName())
                .username(amateurShow.getMember().getUsername())
                .schedule(amateurShow.getSchedule())
                .hashtag(amateurShow.getHashtag())
                .content(amateurShow.getAmateurSummary().getContent())
                .account(amateurShow.getAccount())
                .contact(amateurShow.getContact())
                .amateurStatus(amateurShow.getAmateurStatus())
                .build();
    }

    public static ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO toManageAmateurShowDecideDTO(AmateurShow amateurShow) {
        return ManageAmateurShowResponseDTO.ManageAmateurShowDecideDTO.builder()
                .amateurShowId(amateurShow.getId())
                .amateurShowName(amateurShow.getName())
                .memberName(amateurShow.getMember().getName())
                .username(amateurShow.getMember().getUsername())
                .amateurStatus(amateurShow.getAmateurStatus())
                .rejectReason(amateurShow.getRejectReason())
                .build();
    }
}
