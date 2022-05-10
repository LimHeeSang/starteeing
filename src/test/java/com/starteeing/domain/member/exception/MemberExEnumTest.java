package com.starteeing.domain.member.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberExEnumTest {

    @Test
    void already_exist_member() {
        MemberExEnum memberExEnum = MemberExEnum.ALREADY_EXIST_MEMBER;

        assertThat(memberExEnum.getCode()).isEqualTo("M0001");
        assertThat(memberExEnum.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}