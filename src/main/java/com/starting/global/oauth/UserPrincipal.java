package com.starting.global.oauth;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.MemberRoleEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
    private final String userId;
    private final String password;
    private final ProviderEnum providerEnum;
    private final MemberRoleEnum memberRoleEnum;
    private final Collection<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static UserPrincipal create(Member member, Map<String, Object> attributes) {
        return UserPrincipal.builder()
                .userId(member.getUserId())
                .password(member.getPassword())
                .providerEnum(member.getProviderEnum())
                .memberRoleEnum(MemberRoleEnum.ROLE_USER)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(MemberRoleEnum.ROLE_USER.name())))
                .attributes(attributes)
                .build();
    }
}


