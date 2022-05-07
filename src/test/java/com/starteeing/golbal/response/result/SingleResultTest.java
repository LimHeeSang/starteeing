package com.starteeing.golbal.response.result;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleResultTest {

    @Test
    void create() {
        String testData = "테스트 데이터";
        SingleResult<String> result = new SingleResult<>(testData);

        Assertions.assertThat(result.getData()).isEqualTo(testData);
    }
}