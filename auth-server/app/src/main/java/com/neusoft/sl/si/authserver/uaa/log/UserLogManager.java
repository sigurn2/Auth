package com.neusoft.sl.si.authserver.uaa.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.log.logon.UserLogonLogService;
import com.neusoft.sl.si.authserver.uaa.log.registe.UserRegisterLogService;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

@Component
public class UserLogManager {

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	private static UserLogonLogService userLogonLogService;
	private static UserRegisterLogService userRegisterLogService;

	@Autowired
	public void setUserLogonLogService(UserLogonLogService userLogonLogService) {
		UserLogManager.userLogonLogService = userLogonLogService;
	}

	@Autowired
	public void setUserRegisterLogService(UserRegisterLogService userRegisterLogService) {
		UserLogManager.userRegisterLogService = userRegisterLogService;
	}

	/**
	 * 保存登录日志，同步方式返回信息
	 * 
	 * @param principal
	 * @param request
	 */
	public static UserLogonLog userLogonLogSync(final SaberUserDetails principal, final HttpServletRequest request) {
		return userLogonLogService.UserLogonLog(principal, request);
	}
	
	/**
	 * 更新登出时间和原因
	 * @param logid
	 * @param logoffReason
	 */
	public static void updateLogoffTime(String logid, String logoffReason){
		Validate.notNull(logid, "日志id不能为空");
		UserLogonLog log = new UserLogonLog(logid);
		log.setLogoffReason(logoffReason);
		userLogonLogService.updateLogoffTime(log);
	}

	/**
	 * 保存用户登录日志
	 * 
	 * @param principal
	 * @param request
	 */
	public static void userLogonLog(final SaberUserDetails principal, final HttpServletRequest request) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				userLogonLogService.UserLogonLog(principal, request);
			}
		});
	}

	/**
	 * 保存用户注册日志
	 * 
	 * @param user
	 * @param request
	 */
	public static void saveRegisterLog(final String systemType, final String clientType, final User user,
			final HttpServletRequest request) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				userRegisterLogService.saveRegisterLog(systemType, clientType, user, request);
			}
		});
	}

}
