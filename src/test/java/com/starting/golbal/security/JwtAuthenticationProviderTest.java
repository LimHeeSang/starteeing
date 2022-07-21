package com.starting.golbal.security;

import com.starting.domain.member.exception.NotEqualPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserDetailsServiceImpl userDetailsService;
    @InjectMocks
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Test
    void 패스워드_일치() {
        given(userDetailsService.loadUserByUsername(any())).willReturn(User.builder()
                .username("test_user")
                .authorities(AuthorityUtils.createAuthorityList("ROLE_USER"))
                .password("1234")
                .build());
        given(bCryptPasswordEncoder.matches(any(), any())).willReturn(true);

        Authentication authentication = jwtAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken("test_user", "1234"));

        assertThat(authentication.getName()).isEqualTo("test_user");
        assertThat(authentication.getCredentials()).isEqualTo("1234");
        assertThat((List)authentication.getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    void 패스워드_불일치() {
        given(userDetailsService.loadUserByUsername(any())).willReturn(User.builder()
                .username("test_user")
                .authorities(AuthorityUtils.createAuthorityList("ROLE_USER"))
                .password("1234")
                .build());
        given(bCryptPasswordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() -> jwtAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("test_user", "?")))
                .isInstanceOf(NotEqualPasswordException.class);
    }
}