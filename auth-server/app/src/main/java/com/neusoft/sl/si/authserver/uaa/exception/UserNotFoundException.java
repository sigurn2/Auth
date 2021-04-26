package com.neusoft.sl.si.authserver.uaa.exception;

public class UserNotFoundException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7082501522906552186L;

    public UserNotFoundException(String msg) {
        super(msg);
    }

    protected UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
