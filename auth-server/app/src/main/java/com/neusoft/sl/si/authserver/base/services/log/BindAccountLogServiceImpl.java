package com.neusoft.sl.si.authserver.base.services.log;

import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.log.IPUtil;
import com.neusoft.sl.si.authserver.base.domains.log.BindAccountLog;
import com.neusoft.sl.si.authserver.base.domains.log.BindAccountLogRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class BindAccountLogServiceImpl implements BindAccountLogService {
    @Autowired
    BindAccountLogRepository bindAccountLogRepository;
    @Resource
    private UserService userService;

    @Override
    public BindAccountLog bindAccountLog(SaberUserDetails principal, HttpServletRequest request,String type) {
        BindAccountLog log = new BindAccountLog();
        if(null != request){
            log.setBindIp(IPUtil.getIpAddr(request));

        }
        if (null != principal.getSystemType()) {
            log.setSystemType(principal.getSystemType().toString());
        }
        
//        Date now = new Date();
//        String logonTime = LongDateUtils.toSecondString(now);
//        now.setTime(now.getTime() + 30*60*1000);
//        String logoffTime = LongDateUtils.toSecondString(now);//session超时时间默认30分钟
        log.setSystemType(type);
        log.setBindTime(LongDateUtils.nowAsSecondLong().toString());
        log.setSuccessFlag("1");// 绑定成功失败标志，1为成功，0为失败
        log.setUserAccount(principal.getAccount());// 绑定用户ID
        log.setUserName(principal.getName());// 绑定用户名称
        if (null != principal.getClientType())
        	log.setClientType(principal.getClientType().toString());
//        log.setLogoffTime(logoffTime);
//        log.setLogoffReason("session超时默认30分钟");
        return bindAccountLogRepository.save(log);
    }

    @Override
    public BindAccountLog bindAccountLogFail(SaberUserDetails principal, HttpServletRequest request) {
        BindAccountLog log = new BindAccountLog();
        if(null != request){
            log.setBindIp(IPUtil.getIpAddr(request));

        }
        if (null != principal.getSystemType()) {
            log.setSystemType(principal.getSystemType().toString());
        }

//        Date now = new Date();
//        String logonTime = LongDateUtils.toSecondString(now);
//        now.setTime(now.getTime() + 30*60*1000);
//        String logoffTime = LongDateUtils.toSecondString(now);//session超时时间默认30分钟

        log.setBindTime(LongDateUtils.nowAsSecondLong().toString());
        log.setSuccessFlag("0");// 绑定成功失败标志，1为成功，0为失败
        log.setUserAccount(principal.getAccount());// 绑定用户ID
        log.setUserName(principal.getName());// 绑定用户名称
        if (null != principal.getClientType())
            log.setClientType(principal.getClientType().toString());
//        log.setLogoffTime(logoffTime);
//        log.setLogoffReason("session超时默认30分钟");
        return bindAccountLogRepository.save(log);
    }


}
