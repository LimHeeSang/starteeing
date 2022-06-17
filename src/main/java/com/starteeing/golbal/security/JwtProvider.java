package com.starteeing.golbal.security;

import com.starteeing.domain.member.dto.MemberLoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final String ISSUER = "starting";
    private static final Long ACCESS_TOKEN_VALID_MILLISECOND = 1000 * 60 * 30L;
    private static final Long REFRESH_TOKEN_VALID_MILLISECOND = 1000 * 60 * 60 * 24 * 7L;
    private static final String CLAIM_NAME_ROLES = "roles";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String GRANT_TYPE = "Bearer";

    private final SecretKey key;

    private final UserDetailsServiceImpl userDetailsService;

    public JwtProvider(@Value("${spring.jwt.secret}") String secretKey, UserDetailsServiceImpl userDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    /**
     * Jwt token 생성
     */
    public MemberLoginResponseDto createToken(Authentication authentication) {
        String authorities = getAuthorities(authentication);
        Date now = new Date();

        String accessToken = createAccessToken(authentication, authorities, now);
        String refreshToken = createRefreshToken(now);

        return MemberLoginResponseDto.builder()
                .grantType(GRANT_TYPE)
                .accessTokenExpireDate(now.getTime() + ACCESS_TOKEN_VALID_MILLISECOND)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createRefreshToken(Date now) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_MILLISECOND))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private String createAccessToken(Authentication authentication, String authorities, Date now) {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(authentication.getName())
                .claim(CLAIM_NAME_ROLES, authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_MILLISECOND))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
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
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String parseUserEmail(String token) {
        return parseBody(token)
                .getSubject();
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

    private Claims parseBody(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}