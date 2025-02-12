package muit.backend.service.memberService.socialLogin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "소셜 로그인")
public class OauthController {

    private final GoogleOauthService googleOauthService;

    @GetMapping("/login/oauth2/code/google")
    @Operation(summary = "구글 로그인")
    public AuthResponse loginWithGoogle(@RequestParam("code") String code) {

        return googleOauthService.authenticateWithGoogle(code);
    }

}
