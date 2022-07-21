package com.starting.golbal.response.result;

import com.starting.golbal.exception.common.CommonExEnum;
import com.starting.golbal.exception.common.ExceptionEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResult {

    private String code;
    private String message;

    protected void changeSuccess() {
        code = CommonExEnum.SUCCESS.getCode();
        message = CommonExEnum.SUCCESS.getMessage();
    }

    private void changeFail() {
        code = CommonExEnum.FAIL.getCode();
        message = CommonExEnum.FAIL.getMessage();
    }

    private void changeFail(ExceptionEnum exceptionEnum) {
        code = exceptionEnum.getCode();
        message = exceptionEnum.getMessage();
    }

    public static CommonResult createSuccessResult() {
        CommonResult result = new CommonResult();
        result.changeSuccess();

        return result;
    }

    public static CommonResult createErrorResult() {
        CommonResult result = new CommonResult();
        result.changeFail();

        return result;
    }

    public static CommonResult createErrorResult(ExceptionEnum exceptionEnum) {
        CommonResult result = new CommonResult();
        result.changeFail(exceptionEnum);

        return result;
    }
}