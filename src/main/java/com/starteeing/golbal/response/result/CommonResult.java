package com.starteeing.golbal.response.result;

import com.starteeing.golbal.response.CommonExEnum;
import lombok.Getter;

@Getter
public class CommonResult {

    private String code;
    private String message;

    public void success() {
        code = CommonExEnum.SUCCESS.getCode();
        message = CommonExEnum.SUCCESS.getMessage();
    }

    public void fail() {
        code = CommonExEnum.FAIL.getCode();
        message = CommonExEnum.FAIL.getMessage();
    }
}