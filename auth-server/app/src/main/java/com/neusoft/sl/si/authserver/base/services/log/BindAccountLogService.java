package com.neusoft.sl.si.authserver.base.services.log;

/**
 * @author zhou-hd
 * @modifier y_zhang.neu
 */

import com.neusoft.sl.si.authserver.base.domains.log.BindAccountLog;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

import javax.servlet.http.HttpServletRequest;

public interface BindAccountLogService {
    /**
     * 用户绑定日志记录成功
     *
     * @param principal
     * @param request
     */
    public BindAccountLog bindAccountLog(SaberUserDetails principal, HttpServletRequest request, String type);

    /**
     * 用户绑定日志记录失败
     *
     * @param principal
     * @param request
     */
    public BindAccountLog bindAccountLogFail(SaberUserDetails principal, HttpServletRequest request);

}
