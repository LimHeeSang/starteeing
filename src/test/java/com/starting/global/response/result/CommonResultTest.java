package com.starting.global.response.result;

import com.starting.domain.member.exception.MemberExEnum;
import com.starting.global.exception.common.CommonExEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonResultTest {

    @Test
    void createSuccessResult_메소드_검증() {
        CommonResult result = CommonResult.createSuccessResult();

        assertThat(result.getCode()).isEqualTo(CommonExEnum.SUCCESS.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.SUCCESS.getMessage());
    }

    @Test
    void createErrorResult_메소드_검증() {
        CommonResult result = CommonResult.createErrorResult();

        assertThat(result.getCode()).isEqualTo(CommonExEnum.FAIL.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.FAIL.getMessage());
    }

    @Test
    void createErrorResult2_메소드_검증() {
        CommonResult result = CommonResult.createErrorResult(MemberExEnum.ALREADY_EXIST_MEMBER);

        assertThat(result.getCode()).isEqualTo(MemberExEnum.ALREADY_EXIST_MEMBER.getCode());
        assertThat(result.getMessage()).isEqualTo(MemberExEnum.ALREADY_EXIST_MEMBER.getMessage());
    }
}