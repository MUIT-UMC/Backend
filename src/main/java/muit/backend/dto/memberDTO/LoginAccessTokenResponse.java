package muit.backend.dto.memberDTO;
import lombok.*;
import muit.backend.domain.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAccessTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String username;
    private String name;
    private Role role;
}

