package com.neusoft.sl.si.authserver.uaa.filter.captcha;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaRequestService {

	public String imgCaptchaRequest(String captchaId, String captchaWord);

	public String imgCaptchaRequest(HttpServletRequest request);

	public String smsCaptchaRequest(HttpServletRequest request);

	public String smsCaptchaRequest(String mobilenumber, HttpServletRequest request);

	public String smsCaptchaRequest(String mobilenumber, String captcha);
}
