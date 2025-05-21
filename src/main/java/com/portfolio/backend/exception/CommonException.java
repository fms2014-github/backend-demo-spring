package com.portfolio.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonException extends RuntimeException{

    private int code;

    public CommonException() {
        super();
        this.code = 1000;
    }

    public CommonException(String message) {
        super(message);
        this.code = 1000;
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.code = 1000;
    }

    public CommonException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CommonException(Throwable cause) {
        super(cause);
        this.code = 1000;
    }

    public CommonException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
