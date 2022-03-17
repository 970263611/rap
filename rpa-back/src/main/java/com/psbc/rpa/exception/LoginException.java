package com.psbc.rpa.exception;

/**
 * @author dahua
 * @time 2022/3/14 10:57
 */
public class LoginException extends RpaException {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
