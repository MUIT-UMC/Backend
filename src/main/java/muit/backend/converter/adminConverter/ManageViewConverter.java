package muit.backend.converter.adminConverter;

import muit.backend.domain.entity.musical.Theatre;
import muit.backend.dto.adminDTO.manageViewDTO.ManageViewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageViewConverter {

    public static ManageViewResponseDTO.AdminTheatreResultDTO toAdminTheatreResultDTO(Theatre theatre){
        return ManageViewResponseDTO.AdminTheatreResultDTO.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .build();
    }

    public static ManageViewResponseDTO.AdminTheatreResultListDTO toAdminTheatreResultListDTO(Page<Theatre> theatres){
        List<ManageViewResponseDTO.AdminTheatreResultDTO> theatreResultDTOs = theatres.stream().map(ManageViewConverter::toAdminTheatreResultDTO)
                .collect(Collectors.toList());
        return ManageViewResponseDTO.AdminTheatreResultListDTO.builder()
                .adminTheatreResultDTOList(theatreResultDTOs)
                .build();
    }
}
