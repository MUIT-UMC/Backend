package muit.backend.service.memberService.socialLogin.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KakaoClient {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authorizationCode;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate;

    // accessToken 요청
    public String requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("grant_type", authorizationCode);
        params.add("redirect_uri", redirectUri);

        // 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoAccessTokenResponse> response = restTemplate.postForEntity(tokenUri, request, KakaoAccessTokenResponse.class);

        // HTTP 상태 코드가 200번대가 아니면 예외
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("카카오 로그인 요청 처리 실패");
        }

        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }

    // Profile 정보 요청
    public KakaoAccountProfileResponse requestProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.setBearerAuth(accessToken);

        ResponseEntity<KakaoAccountProfileResponse> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, new HttpEntity<>(headers), KakaoAccountProfileResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("카카오 사용자 정보 요청 실패");
        }

        return response.getBody();
    }
}
