package muit.backend.service.memberService.socialLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleAccessTokenRequest {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
}
