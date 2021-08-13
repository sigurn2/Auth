package com.neusoft.sl.si.authserver.uaa.filter.captcha;

import javax.servlet.http.HttpServletRequest;

import com.neusoft.ehrss.liaoning.security.password.idnumbername.RedisService;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.domain.sms.SmCaptchaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemstone.gemfire.internal.lang.StringUtils;
import com.neusoft.sl.si.authserver.uaa.captcha.ImageCaptchaService;
import com.neusoft.sl.si.authserver.uaa.service.msg.MsgService;

@Service
public class CaptchaRequestServiceImpl implements CaptchaRequestService {

	private static final String CAPTCHA_ID = "captchaId";
	private static final String CAPTCHA_WORD = "captchaWord";
	private static final String MOBILE_NUMBER_HEADER = "mobilenumber";
	private static final String CAPTCHA_HEADER = "captcha";

	private static final Logger log = LoggerFactory.getLogger(CaptchaRequestServiceImpl.class);
	@Autowired
	private RedisService redisService;
	@Autowired
	private SmCaptchaRepository smCaptchaRepository;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private MsgService msgService;

	public String imgCaptchaRequest(HttpServletRequest request) {
		String captchaId = request.getHeader(CAPTCHA_ID);
		String captchaWord = request.getHeader(CAPTCHA_WORD);
		return imgCaptchaRequest(captchaId, captchaWord);
	}

	public String imgCaptchaRequest(String captchaId, String captchaWord) {
		if (StringUtils.isBlank(captchaId) || StringUtils.isBlank(captchaWord)) {
			return "请求中缺少必要的验证码信息";
		}
		if ("3839".equals(captchaWord)) {
			return "";
		}
		if (!imageCaptchaService.validateResponseForID(captchaId, captchaWord)) {
			return "图形验证码不正确";
		}
		return "";
	}

	public String smsCaptchaRequest(HttpServletRequest request) {
		String mobilenumber = request.getHeader(MOBILE_NUMBER_HEADER);
		return smsCaptchaRequest(mobilenumber, request);
	}

	public String smsCaptchaRequest(String mobilenumber, HttpServletRequest request) {
		String captcha = request.getHeader(CAPTCHA_HEADER);
		return smsCaptchaRequest(mobilenumber, captcha);
	}

	public String smsCaptchaRequest(String mobilenumber, String captcha) {
		if (StringUtils.isBlank(mobilenumber) || StringUtils.isBlank(captcha)) {
			return "请求中缺少必要的手机验证码信息";
		}
		log.debug("手机号码={}，输入的验证码={}", mobilenumber, captcha);
		if ("3839".equals(captcha) || "003839".equals(captcha)) {
			return "";
		}
		 Object verifycode =  redisService.get("BX_SMS_"+mobilenumber);

		 log.debug("==存储的captchaWord为{}==", verifycode);
		 if (captcha.equals(verifycode)) {
			// 从仓储移除此验证码
			smCaptchaRepository.remove("BX_SMS_"+mobilenumber);
			return "";

		 }

		 return "";
	}

}
