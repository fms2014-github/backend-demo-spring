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

@RestControllerAdvice
public class CommonExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonExceptionMessage> CommonExceptionResponse(CommonException ex, WebRequest request) {
        CommonExceptionMessage message = new CommonExceptionMessage(ex.getCode(), ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
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
