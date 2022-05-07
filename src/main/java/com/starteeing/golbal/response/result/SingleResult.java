package com.starteeing.golbal.response.result;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {
    private T data;

    public SingleResult(T data) {
        this.data = data;
    }
}