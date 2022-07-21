package com.starting.golbal.exception.common;

import com.starting.golbal.response.ResponseService;
import com.starting.golbal.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResult> handleBindException(MethodArgumentNotValidException e) {
        CommonResult errorResult = responseService.getErrorResult(CommonExEnum.INVALID_BINGING_VALUE);
        return ResponseEntity.badRequest().body(errorResult);
    }
}