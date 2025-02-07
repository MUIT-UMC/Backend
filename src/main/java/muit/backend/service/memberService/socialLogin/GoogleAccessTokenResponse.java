package muit.backend.service.memberService.socialLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleAccessTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
}
