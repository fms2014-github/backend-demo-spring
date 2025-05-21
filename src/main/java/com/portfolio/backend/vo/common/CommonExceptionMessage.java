package com.portfolio.backend.vo.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CommonExceptionMessage {
    private final int code;
    private final String message;
    private final String detail;
    private final LocalDateTime timestamp;

    public CommonExceptionMessage(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.timestamp = LocalDateTime.now();
    }
}
