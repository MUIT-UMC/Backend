package muit.backend.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("http://localhost:8080"); // '*' 대신 명시적 출처 사용
                    config.addAllowedOrigin("http://localhost:5173"); // '*' 대신 명시적 출처 사용
                    config.addAllowedOrigin("http://13.209.69.125:8080"); //배포된 프론트엔드 서버 추가
                    config.addAllowedOrigin("https://muitproject.vercel.app"); //배포된 프론트엔드 주소 추가
                    config.addAllowedOrigin("http://muit-front.s3-website.ap-northeast-2.amazonaws.com"); //배포된 프론트엔드 주소 추가
                    config.addAllowedOrigin("http://muit.site"); //배포된 프론트엔드 주소 추가
                    config.addAllowedOrigin("https://muit.site"); //배포된 프론트엔드 주소 추가
                    config.addAllowedOrigin("https://musical-dodol-afad4e.netlify.app"); //배포된 프론트엔드 주소 추가
                    //config.addAllowedOrigin("*");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    return config;
                }))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 비활성화
                .formLogin(formLogin -> formLogin.disable()) // 폼 로그인 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/index").permitAll() // 홈페이지 접근 허용
                        .requestMatchers("/member/email/login", "/member/register", "/admin/login").permitAll() // 로그인 접근 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // 스웨거 접근 허용
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        //.requestMatchers("/**").permitAll() // 추후 삭제 예정
                        .anyRequest().authenticated()
                )
                .apply(new JwtSecurityConfig(tokenProvider));
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtFilter 등록

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

