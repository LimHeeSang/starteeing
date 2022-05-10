package com.starteeing.golbal.response;

import com.starteeing.golbal.exception.common.CommonExEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonExEnumTest {

    @Test
    void success() {
        CommonExEnum success = CommonExEnum.SUCCESS;
        CommonExEnum success2 = CommonExEnum.valueOf("SUCCESS");

        assertThat(success.getCode()).isEqualTo("E0001");
        assertThat(success.getMessage()).isEqualTo("정상 요청입니다.");

        assertThat(success2).isEqualTo(CommonExEnum.SUCCESS);
    }

    @Test
    void fail() {
        CommonExEnum fail = CommonExEnum.FAIL;

        assertThat(fail.getCode()).isEqualTo("E0002");
        assertThat(fail.getMessage()).isEqualTo("잘못된 요청입니다.");
    }

    @Test
    void invalid_binding_value() {
        CommonExEnum bingingValue = CommonExEnum.INVALID_BINGING_VALUE;

        assertThat(bingingValue.getCode()).isEqualTo("E0003");
        assertThat(bingingValue.getMessage()).isEqualTo("입력값 중 바인딩이 실패하였습니다.");
    }
}