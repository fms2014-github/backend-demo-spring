package com.portfolio.backend.exception;

public class DuplicateKeyException extends CommonException{
    public DuplicateKeyException() {
        super();
    }

    public DuplicateKeyException(String message) {
        super(2000, message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(2000, message, cause);
    }

    public DuplicateKeyException(Throwable cause) {
        super(2000, cause);
    }
}
