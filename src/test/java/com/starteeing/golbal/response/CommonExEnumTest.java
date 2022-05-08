package com.starteeing.golbal.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CommonExEnumTest {

    @Test
    void create() {
        CommonExEnum success = CommonExEnum.SUCCESS;
        CommonExEnum fail = CommonExEnum.FAIL;
        CommonExEnum success2 = CommonExEnum.valueOf("SUCCESS");

        assertThat(success.getCode()).isEqualTo("E0001");
        assertThat(success.getMessage()).isEqualTo("정상 요청입니다.");
        assertThat(fail.getCode()).isEqualTo("E0002");
        assertThat(fail.getMessage()).isEqualTo("잘못된 요청입니다.");
        assertThat(success2).isEqualTo(CommonExEnum.SUCCESS);
    }
}