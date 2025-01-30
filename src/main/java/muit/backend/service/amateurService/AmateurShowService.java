package muit.backend.service.amateurService;

import muit.backend.domain.entity.member.Member;
import muit.backend.dto.amateurDTO.AmateurEnrollRequestDTO;
import muit.backend.dto.amateurDTO.AmateurEnrollResponseDTO;
import muit.backend.dto.amateurDTO.AmateurShowResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmateurShowService {

    AmateurEnrollResponseDTO.EnrollResponseDTO enrollShow(Member member, AmateurEnrollRequestDTO dto,
                                                          MultipartFile posterImage,
                                                          List<MultipartFile> castingImages,
                                                          List<MultipartFile> noticeImages,
                                                          MultipartFile summaryImage);
    AmateurShowResponseDTO getShow(Long amateurId);
}
