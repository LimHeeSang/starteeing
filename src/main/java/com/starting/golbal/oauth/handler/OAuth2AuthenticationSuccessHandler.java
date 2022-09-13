package com.starting.golbal.oauth.handler;

import com.starting.domain.member.dto.MemberLoginResponseDto;
import com.starting.domain.member.entity.Member;
import com.starting.domain.member.exception.NotExistMemberException;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.golbal.oauth.AppProperties;
import com.starting.golbal.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.starting.golbal.oauth.ProviderEnum;
import com.starting.golbal.oauth.userinfo.OAuth2UserInfo;
import com.starting.golbal.oauth.userinfo.OAuth2UserInfoFactory;
import com.starting.golbal.oauth.util.CookieUtil;
import com.starting.golbal.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.starting.golbal.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@Component
@Transactional
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String QUERY_PARAMETER_ACCESS_TOKEN = "accessToken";
    private final JwtProvider jwtProvider;
    private final AppProperties appProperties;
    private final MemberRepository memberRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = getAndValidateRedirectUri(request);

        MemberLoginResponseDto tokenResponseDto = createLoginResponseDto(authentication);

        int cookieMaxAge = (int) (JwtProvider.REFRESH_TOKEN_VALID_MILLISECOND / 60);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, tokenResponseDto.getRefreshToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam(QUERY_PARAMETER_ACCESS_TOKEN, tokenResponseDto.getAccessToken())
                .build().toUriString();
    }

    private MemberLoginResponseDto createLoginResponseDto(Authentication authentication) {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderEnum providerType = ProviderEnum.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        MemberLoginResponseDto tokenResponseDto = jwtProvider.createToken(authToken);
        Member member = memberRepository.findByUserId(userInfo.getUserId()).orElseThrow(NotExistMemberException::new);
        member.saveRefreshToken(tokenResponseDto.getRefreshToken());
        return tokenResponseDto;
    }

    private String getAndValidateRedirectUri(HttpServletRequest request) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        return redirectUri.orElse(getDefaultTargetUrl());
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return validateHostAndPort(clientRedirectUri, authorizedURI);
                });
    }

    private boolean validateHostAndPort(URI clientRedirectUri, URI authorizedURI) {
        return validateHost(clientRedirectUri, authorizedURI) &&
                validatePort(clientRedirectUri, authorizedURI);
    }

    private boolean validatePort(URI clientRedirectUri, URI authorizedURI) {
        return authorizedURI.getPort() == clientRedirectUri.getPort();
    }

    private boolean validateHost(URI clientRedirectUri, URI authorizedURI) {
        return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost());
    }
}

