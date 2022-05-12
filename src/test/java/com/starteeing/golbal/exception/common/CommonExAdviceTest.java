package com.starteeing.golbal.exception.common;

import com.starteeing.golbal.response.ResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonExAdviceTest {

    @Autowired
    CommonExAdvice commonExAdvice;
    @Autowired
    ResponseService responseService;

    @Test
    void handle_bindException() {
    }
}