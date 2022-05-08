package com.starteeing.golbal.response.result;

import com.starteeing.golbal.response.CommonEnum;
import lombok.Getter;

@Getter
public class CommonResult {

    private String code;
    private String message;

    public void success() {
        code = CommonEnum.SUCCESS.getCode();
        message = CommonEnum.SUCCESS.getMessage();
    }

    public void fail() {
        code = CommonEnum.FAIL.getCode();
        message = CommonEnum.FAIL.getMessage();
    }
}