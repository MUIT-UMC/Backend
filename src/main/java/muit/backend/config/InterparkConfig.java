package muit.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterparkConfig {
    @Value("${interparkticket.ranking_request_url}")
    private String rankingRequestUrl;

    // 랭킹 url 요청하는 메서드
    public String getRankingUrl() {
        return rankingRequestUrl;
    }
}
