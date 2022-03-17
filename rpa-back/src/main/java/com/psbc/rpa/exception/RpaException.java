package com.psbc.rpa.exception;

/**
 * @author dahua
 * @time 2022/3/14 10:56
 */
public class RpaException extends Exception{

    public RpaException() {
    }

    public RpaException(String message) {
        super(message);
    }

    public RpaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpaException(Throwable cause) {
        super(cause);
    }

    public RpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
