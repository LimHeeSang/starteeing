package com.starting.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starting.global.exception.common.CommonExEnum;
import com.starting.global.response.ResponseService;
import com.starting.global.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ResponseService responseService;
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResult errorResult = responseService.getErrorResult(CommonExEnum.FORBIDDEN);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResult));
    }
}