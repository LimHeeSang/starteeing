package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ExistMemberExceptionTest {

    @Test
    void create() {
        ExistMemberException e = new ExistMemberException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.ALREADY_EXIST_MEMBER);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}