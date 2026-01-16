package com.gym.system.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

public class RestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(RestLoggingInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        log.info(
            "Incoming request: method={} uri={} query={}",
            request.getMethod(),
            request.getRequestURI(),
            request.getQueryString()
        );
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex
    ) {
        if (ex != null) {
            log.error(
                "Request failed: method={} uri={} status={} error={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                ex.getMessage(),
                ex
            );
        } else {
            log.info(
                "Request completed: method={} uri={} status={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus()
            );
        }
    }
}
