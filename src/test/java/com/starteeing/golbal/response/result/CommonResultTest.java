package com.starteeing.golbal.response.result;

import com.starteeing.golbal.response.CommonEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonResultTest {

    CommonResult result = new CommonResult();

    @Test
    void success메소드_검증() {
        result.success();

        assertThat(result.getCode()).isEqualTo(CommonEnum.SUCCESS.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonEnum.SUCCESS.getMessage());
    }

    @Test
    void fail메소드_검증() {
        result.fail();

        assertThat(result.getCode()).isEqualTo(CommonEnum.FAIL.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonEnum.FAIL.getMessage());
    }
}