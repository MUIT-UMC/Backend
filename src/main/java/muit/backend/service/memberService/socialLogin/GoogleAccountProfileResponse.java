package muit.backend.service.memberService.socialLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleAccountProfileResponse {
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
