package com.springBoot.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonException extends RuntimeException{

    private int errorCode;

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(int errorCode, String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(int errorCode, String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(int errorCode, Throwable cause) {
        super(cause);
    }
}
