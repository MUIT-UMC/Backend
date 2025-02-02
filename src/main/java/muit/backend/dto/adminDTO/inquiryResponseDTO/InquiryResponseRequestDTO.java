package muit.backend.dto.adminDTO.inquiryResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class InquiryResponseRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryResponseUpsertDTO {
        private String content;
    }
}
