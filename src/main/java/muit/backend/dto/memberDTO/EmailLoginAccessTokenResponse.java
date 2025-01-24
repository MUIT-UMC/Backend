package muit.backend.dto.memberDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailLoginAccessTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;

}

