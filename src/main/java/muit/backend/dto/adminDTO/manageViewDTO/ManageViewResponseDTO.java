package muit.backend.dto.adminDTO.manageViewDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

public class ManageViewResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminTheatreResultDTO {
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminTheatreResultListDTO {
        private List<AdminTheatreResultDTO> adminTheatreResultDTOList;
    }
}
