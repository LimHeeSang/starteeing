package com.starting.domain.member.entity;

import com.starting.domain.common.BaseTimeEntity;
import com.starting.domain.member.dto.InputUserDataRequestDto;
import com.starting.global.oauth.ProviderEnum;
import com.starting.global.oauth.userinfo.OAuth2UserInfo;
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

    @Column(unique = true, nullable = false)
    private String userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
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
        this.refreshToken = RefreshToken.builder()
                .member(this)
                .refreshToken(token)
                .build();
    }

    public Optional<RefreshToken> getRefreshToken() {
        return Optional.ofNullable(refreshToken);
    }

    public void updateOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo) {
        if (!imageProfileUrl.equals(oAuth2UserInfo.getImageUrl())) {
            imageProfileUrl = oAuth2UserInfo.getImageUrl();
        }
    }

    public void inputUserData(InputUserDataRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}