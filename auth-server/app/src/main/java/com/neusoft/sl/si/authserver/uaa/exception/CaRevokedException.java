package com.neusoft.sl.si.authserver.uaa.exception;

public class CaRevokedException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4537640576712586434L;

    public CaRevokedException(String msg) {
        super(msg);
    }
}
