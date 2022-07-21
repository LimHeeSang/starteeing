package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotEqualPasswordExceptionTest {

    @Test
    void create() {
        NotEqualPasswordException e = new NotEqualPasswordException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.NOT_EQUAL_PASSWORD);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(e.getMessage()).isEqualTo("패스워드가 일치하지 않습니다.");
    }
}