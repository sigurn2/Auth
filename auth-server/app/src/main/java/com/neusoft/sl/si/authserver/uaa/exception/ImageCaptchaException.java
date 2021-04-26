package com.neusoft.sl.si.authserver.uaa.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误
 * 
 * @author wuyf
 * 
 */
public class ImageCaptchaException extends AuthenticationException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8423918222119323175L;

    public ImageCaptchaException(String message) {
        super(message);
    }

}
