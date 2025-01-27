package muit.backend.config.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://13.209.69.125:8080") // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")  // 모든 헤더 허용
                .maxAge(3600)
                .allowCredentials(true); // 쿠키 인증 요청 허용
        // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }

}