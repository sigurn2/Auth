package com.neusoft.sl.si.authserver.uaa.exception;

public class BusinessException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }

    protected BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
