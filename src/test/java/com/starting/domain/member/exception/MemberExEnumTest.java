package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberExEnumTest {

    @Test
    void already_exist_member() {
        MemberExEnum memberExEnum = MemberExEnum.ALREADY_EXIST_MEMBER;

        assertThat(memberExEnum.getCode()).isEqualTo("M0001");
        assertThat(memberExEnum.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void not_exist_member() {
        MemberExEnum memberExEnum = MemberExEnum.NOT_EXIST_MEMBER;

        assertThat(memberExEnum.getCode()).isEqualTo("M0002");
        assertThat(memberExEnum.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    void not_valid_token() {
        MemberExEnum memberExEnum = MemberExEnum.NOT_VALID_TOKEN;

        assertThat(memberExEnum.getCode()).isEqualTo("M0003");
        assertThat(memberExEnum.getMessage()).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    void not_exist_token() {
        MemberExEnum memberExEnum = MemberExEnum.NOT_EXIST_TOKEN;

        assertThat(memberExEnum.getCode()).isEqualTo("M0004");
        assertThat(memberExEnum.getMessage()).isEqualTo("토큰이 존재하지 않습니다.");
    }

    @Test
    void not_equal_password() {
        MemberExEnum memberExEnum = MemberExEnum.NOT_EQUAL_PASSWORD;

        assertThat(memberExEnum.getCode()).isEqualTo("M0005");
        assertThat(memberExEnum.getMessage()).isEqualTo("패스워드가 일치하지 않습니다.");
    }

    @Test
    void already_exist_nickname() {
        MemberExEnum memberExEnum = MemberExEnum.ALREADY_EXIST_NICKNAME;

        assertThat(memberExEnum.getCode()).isEqualTo("M0006");
        assertThat(memberExEnum.getMessage()).isEqualTo("해당 닉네임이 이미 존재합니다.");
    }

    @Test
    void already_input_userdata() {
        MemberExEnum memberExEnum = MemberExEnum.ALREADY_INPUT_USERDATA;

        assertThat(memberExEnum.getCode()).isEqualTo("M0007");
        assertThat(memberExEnum.getMessage()).isEqualTo("이미 유저 정보를 입력했습니다.");
    }
}