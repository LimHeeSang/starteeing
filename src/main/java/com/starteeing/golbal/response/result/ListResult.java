package com.starteeing.golbal.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListResult<T> extends CommonResult {
    private List<T> data;
}