package com.starting.golbal.oauth;

import com.starting.domain.member.entity.Member;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.repository.MemberRepository;
import com.starting.golbal.oauth.userinfo.OAuth2UserInfo;
import com.starting.golbal.oauth.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderEnum providerEnum = ProviderEnum.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerEnum, user.getAttributes());
        Optional<Member> findMember = memberRepository.findByUserId(userInfo.getUserId());

        if (findMember.isPresent()) {
            return updateMember(user, userInfo, findMember.get());
        }

        UserMember createMember = createMember(providerEnum, userInfo);
        return createUserPrincipal(user, createMember);
    }

    private UserPrincipal updateMember(OAuth2User user, OAuth2UserInfo userInfo, Member updateMember) {
        updateMember.updateOAuth2UserInfo(userInfo);
        return createUserPrincipal(user, updateMember);
    }

    private UserMember createMember(ProviderEnum providerEnum, OAuth2UserInfo userInfo) {
        UserMember createMember = userInfo.toEntity(providerEnum);
        memberRepository.save(createMember);
        return createMember;
    }

    private UserPrincipal createUserPrincipal(OAuth2User user, Member member) {
        return UserPrincipal.create(member, user.getAttributes());
    }
}