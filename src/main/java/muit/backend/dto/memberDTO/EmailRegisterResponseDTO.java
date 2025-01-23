package muit.backend.dto.memberDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailRegisterResponseDTO {
    private Long id;
    @Email
    private String email;
}