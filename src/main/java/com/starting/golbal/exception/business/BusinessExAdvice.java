package com.starting.golbal.exception.business;

import com.starting.golbal.response.ResponseService;
import com.starting.golbal.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class BusinessExAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<CommonResult> handleBusinessException(BusinessException e) {
        CommonResult errorResult = responseService.getErrorResult(e.getExceptionEnum());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(errorResult);
    }
}