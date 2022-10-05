package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ExistNicknameExceptionTest {

    @Test
    void create() {
        ExistNicknameException e = new ExistNicknameException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.ALREADY_EXIST_NICKNAME);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("해당 닉네임이 이미 존재합니다.");
    }
}