package com.starteeing.golbal.response.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleResult<T> extends CommonResult {
    private T data;

    private void putData(T data) {
        this.data = data;
    }

    public static <T> SingleResult<T> createSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.putData(data);
        result.changeSuccess();

        return result;
    }
}