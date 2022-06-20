package com.starteeing.domain.member.entity;

import com.starteeing.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @OneToOne(mappedBy = "refreshToken", fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        refreshToken = updateRefreshToken;
    }

    public boolean isEqualTokenValue(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }
}