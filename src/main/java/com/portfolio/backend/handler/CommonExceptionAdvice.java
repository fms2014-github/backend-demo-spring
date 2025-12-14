package com.portfolio.backend.handler;

import com.portfolio.backend.exception.CommonException;
import com.portfolio.backend.vo.common.CommonExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class CommonExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonExceptionMessage> CommonExceptionResponse(CommonException ex, WebRequest request) {
        CommonExceptionMessage message = new CommonExceptionMessage(ex.getCode(), ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String resourcePath = ex.getResourcePath();

        // 1. Swagger 관련이나 .map 파일이면 로그 없이 조용히 404 반환
        if (resourcePath.contains("swagger-ui") || resourcePath.endsWith(".map")) {
            return ResponseEntity.notFound().build();
        }

        // 2. 그 외의 진짜 누락된 리소스는 로그 남기기 (선택 사항)
         logger.error(String.format("Missing resource: %s", resourcePath));

        // 3. 나머지는 부모 클래스의 기본 처리 로직에 맡김 (표준 404 응답 생성)
        return super.handleNoResourceFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        logger.error("###### Error Handler ######");
        logger.error(ex.getMessage());
        logger.error(ex.getLocalizedMessage());
        logger.error(ex.getClass().getName());
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
