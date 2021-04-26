package com.neusoft.sl.si.authserver.uaa.log.logon;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.log.IPUtil;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLogRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

@Service
public class UserLogonLogServiceImpl implements UserLogonLogService {
    @Autowired
    UserLogonLogRepository userLogonLogRepository;
    @Resource
    private UserService userService;

    @Override
    public UserLogonLog UserLogonLog(SaberUserDetails principal, HttpServletRequest request) {
        UserLogonLog log = new UserLogonLog();
        if(null != request)
        	log.setLogonIp(IPUtil.getIpAddr(request));
        if (null != principal.getSystemType()) {
            log.setSystemType(principal.getSystemType().toString());
        }
        
//        Date now = new Date();
//        String logonTime = LongDateUtils.toSecondString(now);
//        now.setTime(now.getTime() + 30*60*1000);
//        String logoffTime = LongDateUtils.toSecondString(now);//session超时时间默认30分钟
        
        log.setLogonTime(LongDateUtils.nowAsSecondLong().toString());
        log.setSuccessFlag("1");// 登录成功失败标志，1为成功，0为失败
        log.setUserAccount(principal.getAccount());// 登录用户ID
        log.setUserName(principal.getName());// 登录用户名称
        if (null != principal.getClientType())
        	log.setClientType(principal.getClientType().toString());
//        log.setLogoffTime(logoffTime);
//        log.setLogoffReason("session超时默认30分钟");
        return userLogonLogRepository.save(log);
    }

	@Override
	public void updateLogoffTime(UserLogonLog log) {
		UserLogonLog logonLog = this.userLogonLogRepository.findOne(log.getId());
		if(logonLog != null){
			logonLog.setLogoffTime(LongDateUtils.nowAsSecondLong().toString());
			logonLog.setLogoffReason(log.getLogoffReason());
			this.userLogonLogRepository.save(logonLog);
		}
	}

}
