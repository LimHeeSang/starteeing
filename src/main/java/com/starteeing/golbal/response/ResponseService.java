package com.starteeing.golbal.response;

import com.starteeing.golbal.response.result.CommonResult;
import com.starteeing.golbal.response.result.ListResult;
import com.starteeing.golbal.response.result.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    
    /**
     * 단일 결과 성공 처리
     */
    public <T> SingleResult<T> createSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>(data);
        result.success();
        return result;
    }

    /**
     * 복수 결과 성공 처리
     */
    public <T> ListResult<T> createListResult(List<T> data) {
        ListResult<T> result = new ListResult<>(data);
        result.success();
        return result;
    }

    /**
     * 성공 결과만 처리
     */
    public CommonResult createSuccessResult() {
        CommonResult result = new CommonResult();
        result.success();
        return result;
    }

    /**
     * 실패 결과만 처리
     */
    public CommonResult createFailResult() {
        CommonResult result = new CommonResult();
        result.fail();
        return result;
    }
}