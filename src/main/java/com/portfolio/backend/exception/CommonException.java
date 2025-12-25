package com.portfolio.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonException extends RuntimeException{

    private int errorCode;

    public CommonException() {
        super();
        this.errorCode = 1000;
    }

    public CommonException(String message) {
        super(message);
        this.errorCode = 1000;
    }

    public CommonException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 1000;
    }

    public CommonException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CommonException(Throwable cause) {
        super(cause);
        this.errorCode = 1000;
    }

    public CommonException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
