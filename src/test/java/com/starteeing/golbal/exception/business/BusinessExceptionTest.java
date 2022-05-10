package com.starteeing.golbal.exception.business;

import com.starteeing.domain.member.exception.MemberExEnum;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessExceptionTest {

    @Test
    void create() {
        BusinessException e = new BusinessException(MemberExEnum.ALREADY_EXIST_MEMBER);

        assertThat(e.getExceptionEnum()).isEqualTo(MemberExEnum.ALREADY_EXIST_MEMBER);
        assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}