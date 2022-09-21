package com.starting.global.response;

import com.starting.global.exception.common.ExceptionEnum;
import com.starting.global.response.result.CommonResult;
import com.starting.global.response.result.ListResult;
import com.starting.global.response.result.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    
    /**
     * 단일 데이터 + 성공 응답 처리
     */
    public <T> SingleResult<T> getSingleResult(T data) {
        return SingleResult.createSingleResult(data);
    }

    /**
     * 복수 데이터 + 성공 응답 처리
     */
    public <T> ListResult<T> getListResult(List<T> data) {
        return ListResult.createListResult(data);
    }

    /**
     * 성공 응답 처리
     */
    public CommonResult getSuccessResult() {
        return CommonResult.createSuccessResult();
    }

    /**
     * 에러 응답 처리
     */
    public CommonResult getErrorResult() {
        return CommonResult.createErrorResult();
    }

    /**
     * 넘어온 에러 정보에 근거하여 에러 응답 처리
     */
    public CommonResult getErrorResult(ExceptionEnum exceptionEnum) {
        return CommonResult.createErrorResult(exceptionEnum);
    }
}