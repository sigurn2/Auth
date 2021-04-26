/**
 * 
 */
package com.neusoft.sl.si.authserver.uaa.exception;

import com.neusoft.sl.girder.ddd.core.exception.NestedSystemException;

/**
 * @author mojf
 * 
 */
public class WebserviceApiException extends NestedSystemException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7589935670559529911L;

    /**
     * @param msg
     */
    public WebserviceApiException(String msg) {
        super(msg);
    }

    public WebserviceApiException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
