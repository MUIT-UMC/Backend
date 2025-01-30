package muit.backend.service.amateurService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.amateurDTO.AmateurEnrollRequestDTO;
import muit.backend.dto.amateurDTO.AmateurEnrollResponseDTO;
import muit.backend.dto.amateurDTO.AmateurShowResponseDTO;

public interface AmateurShowService {

    AmateurEnrollResponseDTO.EnrollResponseDTO enrollShow(Member member, AmateurEnrollRequestDTO dto);
    AmateurShowResponseDTO getShow(Long amateurId);
}
