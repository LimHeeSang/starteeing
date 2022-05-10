package com.starteeing.golbal.exception.business;

import com.starteeing.golbal.exception.common.ExceptionEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionEnum exceptionEnum;

    public BusinessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public HttpStatus getHttpStatus() {
        return exceptionEnum.getHttpStatus();
    }
}