package muit.backend.service.memberService.socialLogin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final GoogleOauthService googleOauthService;

    @GetMapping("/login/oauth2/code/google")
    public AuthResponse loginWithGoogle(@RequestParam("code") String code) {

        return googleOauthService.authenticateWithGoogle(code);
    }

}
