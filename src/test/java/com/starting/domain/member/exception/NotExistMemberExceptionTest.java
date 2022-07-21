package com.starting.domain.member.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class NotExistMemberExceptionTest {

    @Test
    void create() {
        NotExistMemberException e = new NotExistMemberException();

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.NOT_EXIST_MEMBER);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }
}