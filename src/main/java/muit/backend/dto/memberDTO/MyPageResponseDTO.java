package muit.backend.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import muit.backend.domain.enums.ActiveStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyPageResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private ActiveStatus status;
}
