package muit.backend.config.jwt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
