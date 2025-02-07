package muit.backend.service.memberService.socialLogin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SocialConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
