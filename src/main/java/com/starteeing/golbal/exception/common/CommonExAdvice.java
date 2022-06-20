package com.starteeing.golbal.exception.common;

import com.starteeing.golbal.response.ResponseService;
import com.starteeing.golbal.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<CommonResult> handleBindException(HttpMessageNotReadableException e) {
        CommonResult errorResult = responseService.getErrorResult(CommonExEnum.INVALID_BINGING_VALUE);
        return ResponseEntity.badRequest().body(errorResult);
    }
}