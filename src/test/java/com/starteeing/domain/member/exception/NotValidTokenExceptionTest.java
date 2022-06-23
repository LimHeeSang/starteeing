package com.starteeing.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotValidTokenExceptionTest {
    @Test
    void create() {
        NotValidTokenException e = new NotValidTokenException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.NOT_VALID_TOKEN);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(e.getMessage()).isEqualTo("유효하지 않은 토큰입니다.");
    }
}