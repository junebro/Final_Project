package com.final_project.utility;

import com.final_project.dto.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private static final long ACCESS_TOKEN_VALIDITY = 86400000; // 1 day
    private static final long REFRESH_TOKEN_VALIDITY = 2592000000L; // 30 days

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_VALIDITY);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        System.out.println("Generated Access Token: " + accessToken);
        System.out.println("Generated Refresh Token: " + refreshToken);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            throw new RuntimeException("토큰이 제공되지 않았습니다.");
        }

        Claims claims;
        try {
            claims = parseClaims(accessToken);
        } catch (Exception e) {
            throw new RuntimeException("토큰 파싱 중 오류가 발생했습니다.", e);
        }
        System.out.println("getAuthentication");
        System.out.println(claims);
        String authInfo = claims.get("auth", String.class);

        if (authInfo == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 빈 문자열인 경우도 체크
        if (authInfo.isEmpty()) {
            throw new RuntimeException("권한 정보가 유효하지 않습니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(authInfo.split(","))
                .filter(auth -> !auth.isEmpty()) // 빈 문자열 필터링
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        if (authorities.isEmpty()) {
            throw new RuntimeException("유효한 권한 정보가 포함되어 있지 않습니다.");
        }

        // UserDetails 객체를 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {

        String tokenString = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsImF1dGgiOiIiL…2MDN9.jLkjdtATDkVMfW-2jnWKfsRviX0neaiXsUA_rz0RmXs";

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            return claimsJws.getBody().getSubject(); // 'subject' 클레임을 사용자 ID로 사용
        } catch (Exception e) {
            System.err.println("Invalid token");
            return null;
        }
    }

    // 토큰에서 mbrno를 추출
    public Integer getMbrno(String token) {
        Claims claims = parseClaims(token);
        return claims.get("mbrno", Integer.class);
    }
}
