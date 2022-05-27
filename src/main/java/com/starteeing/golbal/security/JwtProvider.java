package com.starteeing.golbal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Component
public class JwtProvider {

    private static final String ISSUER = "starting";
    private static final Long TOKEN_VALID_MILLISECOND = 60 * 60 * 1000L;
    public static final String HEADER_NAME_X_AUTH_TOKEN = "X-AUTH-TOKEN";
    public static final String CLAIM_NAME_ROLES = "roles";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private SecretKey key;

    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Jwt token 생성
     */
    public String createToken(Authentication authentication) {
        Date now = new Date();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(authentication.getName())
                .claim(CLAIM_NAME_ROLES, authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILLISECOND))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Jwt 로 인증정보 조회
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(parseUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Header 에서 Token Parsing
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME_X_AUTH_TOKEN);
    }

    /**
     * Jwt 의 유효성 및 만료일짜 확인
     */
    public boolean validateToken(String token) {
        try {
            Claims body = parseBody(token);
            return !body.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private String parseUserEmail(String token) {
        return parseBody(token)
                .getSubject();
    }

    private Claims parseBody(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}