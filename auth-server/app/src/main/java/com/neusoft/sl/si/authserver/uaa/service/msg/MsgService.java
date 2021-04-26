package com.neusoft.sl.si.authserver.uaa.service.msg;

import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.BatchMsgDto;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.MsgType;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.SmsCaptchaDTO;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.domain.sms.SmCaptcha;
import com.neusoft.sl.si.authserver.uaa.log.enums.BusinessType;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

/**
 * 消息操作接口
 * 
 * @author y_zhang.neu
 *
 */
public interface MsgService {

	public SmCaptcha nextCaptchaPersonSms(String mobilenumber, String webacc, BusinessType businessType, ClientType clientType, String channel, String sender);

	public SmCaptcha nextCaptchaSms(String mobilenumber, String webacc, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender);

	/**
	 * 创建短信验证码
	 * 
	 * @param smsMsgDto
	 * @return
	 */
	public SmCaptcha nextCaptcha(String mobilenumber, String webacc, MsgType msgType, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender);

	public SmsCaptchaDTO nextCaptchaNoSave(String mobilenumber, String webacc, MsgType msgType, SystemType systemType, UserType userType, BusinessType businessType, ClientType clientType, String channel, String sender);
	
	public boolean validateCaptcha(String mobile, String verifycode);

	public void sendMsg(BatchMsgDto dto);
}
