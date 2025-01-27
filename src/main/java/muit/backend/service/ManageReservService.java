package muit.backend.service;

import muit.backend.dto.manageReservDTO.ManageReservResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManageReservService {

    public Page<ManageReservResponseDTO.ManageReservResultListDTO> getAllReservations(Pageable pageable, String keyword, Set<String> selectedFields);
}
