package com.starting.global.response.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResult<T> extends CommonResult {

    private List<T> data;

    private void putData(List<T> data) {
        this.data = data;
    }

    public static <T> ListResult<T> createListResult(List<T> data) {
        ListResult<T> result = new ListResult<>();
        result.putData(data);
        result.changeSuccess();

        return result;
    }
}