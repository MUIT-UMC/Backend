package muit.backend.service.adminService;

import muit.backend.dto.adminDTO.manageMemberDTO.ManageMemberRequestDTO;
import muit.backend.dto.adminDTO.manageMemberDTO.ManageMemberResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageMemberService {

    // 사용자 전체 조회
    public Page<ManageMemberResponseDTO.ManageMemberResultListDTO> getAllMembers(Pageable pageable, String keyword, Set<String> selectedFields);

    // 특정 사용자 조회 (단건 조회)
    public ManageMemberResponseDTO.ManageMemberResultDTO getMember(Long memberId);

    // 특정 사용자 정보 수정
    public ManageMemberResponseDTO.ManageMemberResultDTO updateMember(Long memberId, ManageMemberRequestDTO.UpdateMemberRequestDTO requestDTO);
}
