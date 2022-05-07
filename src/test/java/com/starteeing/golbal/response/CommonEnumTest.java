package com.starteeing.golbal.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonEnumTest {

    @Test
    void create() {
        CommonEnum success = CommonEnum.SUCCESS;
        CommonEnum fail = CommonEnum.FAIL;
        CommonEnum success2 = CommonEnum.valueOf("SUCCESS");

        assertThat(success.getCode()).isEqualTo("E0001");
        assertThat(success.getMessage()).isEqualTo("정상 요청입니다.");
        assertThat(fail.getCode()).isEqualTo("E0002");
        assertThat(fail.getMessage()).isEqualTo("잘못된 요청입니다.");
        assertThat(success2).isEqualTo(CommonEnum.SUCCESS);
    }
}