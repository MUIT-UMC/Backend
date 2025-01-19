package muit.backend.service;

import muit.backend.dto.manageMemberDTO.ManageMemberResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageMemberService {

    // 사용자 전체 조회
    public Page<ManageMemberResponseDTO.ManageMemberResultListDTO> getAllMembers(Pageable pageable, String keyword, Set<String> selectedFields);
}
