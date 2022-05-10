package com.starteeing.golbal.exception.common;

import org.springframework.http.HttpStatus;

public interface ExceptionEnum {

    String getCode();

    String getMessage();

    HttpStatus getHttpStatus();
}