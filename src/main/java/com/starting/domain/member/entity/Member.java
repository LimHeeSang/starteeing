package com.starting.domain.member.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.golbal.oauth.ProviderEnum;
import com.starting.golbal.oauth.userinfo.OAuth2UserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Entity
public abstract class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String imageProfileUrl;

    @Enumerated(value = EnumType.STRING)
    private ProviderEnum providerEnum;

    @Column(nullable = false)
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    protected List<MemberRole> memberRoles = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    public List<MemberRoleEnum> mapToMemberRoleEnum() {
        return memberRoles.stream()
                .map(MemberRole::getMemberRoleEnum)
                .collect(Collectors.toList());
    }

    public void saveRefreshToken(String token) {
        RefreshToken refreshToken  = RefreshToken.builder()
                .member(this)
                .refreshToken(token)
                .build();

        this.refreshToken = refreshToken;
    }

    public Optional<RefreshToken> getRefreshToken() {
        return Optional.ofNullable(refreshToken);
    }

    public void updateOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo) {
        if (!name.equals(oAuth2UserInfo.getName())) {
            name = oAuth2UserInfo.getName();
        }
        if (!imageProfileUrl.equals(oAuth2UserInfo.getImageUrl())) {
            imageProfileUrl = oAuth2UserInfo.getImageUrl();

        }
    }
}