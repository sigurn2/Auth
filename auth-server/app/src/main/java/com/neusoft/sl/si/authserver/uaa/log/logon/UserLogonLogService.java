package com.neusoft.sl.si.authserver.uaa.log.logon;

/**
 * @author zhou-hd
 * @modifier y_zhang.neu
 */
import javax.servlet.http.HttpServletRequest;

import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public interface UserLogonLogService {
    /**
     * 用户登录日志记录
     * 
     * @param principal
     * @param request
     */
    public UserLogonLog UserLogonLog(SaberUserDetails principal, HttpServletRequest request);
    
    /**
     * 更新登出时间
     * @param log
     */
    public void updateLogoffTime(UserLogonLog log);
}
