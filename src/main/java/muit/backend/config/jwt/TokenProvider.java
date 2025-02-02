package muit.backend.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 5;            // 5시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        log.info("JWT Secret Key (Generation): {}", secretKey); // Secret Key 출력

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

       /* if (authorities.isEmpty()) {
            authorities = "ROLE_USER"; // 기본 권한 부여 //지피티가 추가함
        }*/
        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDTO.builder()
                //.grantType(BEARER_TYPE)
                .accessToken(accessToken)
                //.accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        log.info("🔍 클레임까지 옴 Claims: {}", claims);  // 여기서 claims 값을 확인

        // 🔍 auth 값이 존재하는 경우, GrantedAuthority로 변환
//        String authorities = claims.get("auth", String.class);
//        List<SimpleGrantedAuthority> grantedAuthorities = Collections.emptyList();
//
//        if (authorities != null && !authorities.isEmpty()) {
//            grantedAuthorities = Arrays.stream(authorities.split(","))
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        }
//
//        // 권한 정보를 포함한 UserDetails 생성
//        UserDetails principal = new User(claims.getSubject(), "", grantedAuthorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
//
         //권한 정보 없이 UserDetails 생성
        UserDetails principal = new User(claims.getSubject(), "", new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }



    public boolean validateToken(String token) {
        log.info("JWT Secret Key (Validation): {}", Base64.getEncoder().encodeToString(key.getEncoded())); // Secret Key 출력

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}