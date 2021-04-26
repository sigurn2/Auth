package com.neusoft.sl.si.authserver.uaa.filter.captcha.img;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.si.authserver.uaa.captcha.ImageCaptchaService;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.token.UserPasswordWithImgCaptchaAuthenticationToken;
import com.neusoft.sl.si.authserver.uaa.tools.DESUtil;

public class UserPasswordWithImgCaptchaRequestProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public UserPasswordWithImgCaptchaRequestProcessingFilter(String filterProcessesUrl, String failureUrl) {
        super(filterProcessesUrl);
        if (failureUrl != null && !"".equals(failureUrl)) {
            SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
            failureHandler.setDefaultFailureUrl(failureUrl);
            super.setAuthenticationFailureHandler(failureHandler);
        }
    }

    /*
     * 生成 Token
     * 
     * @see org.springframework.security.web.authentication.
     * AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.
     * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
    	Validate.notBlank(request.getParameter("username"), messages.getMessage("username.notBlank", "用户名不能为空"));
    	Validate.notBlank(request.getParameter("password"), messages.getMessage("password.notBlank", "密码不能为空"));
        // 解密
        UserPasswordWithImgCaptchaAuthenticationToken authRequest = null;
        String des = SpringContextUtils.getApplicationContext().getEnvironment().getProperty("security.basic.des");
        if ("true".equals(des)) {
            String account = DESUtil.strDec(request.getParameter("username"), "auth", "nps", "web");
            String password = DESUtil.strDec(request.getParameter("password"), "auth", "nps", "web");
            authRequest = new UserPasswordWithImgCaptchaAuthenticationToken(account, password,
                    request.getParameter("captchaId"), request.getParameter("captchaWord"));

        } else if ("false".equals(des)) {
            authRequest = new UserPasswordWithImgCaptchaAuthenticationToken(request.getParameter("username"),
                    request.getParameter("password"), request.getParameter("captchaId"),
                    request.getParameter("captchaWord"));
        } else {
            // 默认加密传输
            String account = DESUtil.strDec(request.getParameter("username"), "auth", "nps", "web");
            String password = DESUtil.strDec(request.getParameter("password"), "auth", "nps", "web");
            authRequest = new UserPasswordWithImgCaptchaAuthenticationToken(account, password,
                    request.getParameter("captchaId"), request.getParameter("captchaWord"));
        }
        // 校验图形验证码
        String captchaId = request.getParameter("captchaId");
        String captchaWord = request.getParameter("captchaWord");
        if (StringUtils.isEmpty(captchaId) || StringUtils.isEmpty(captchaWord)) {
        	setCookieUserNameAndPassWord(response, request.getParameter("username"), request.getParameter("password"));
            throw new CaptchaErrorException("请求中缺少必要的验证码信息！");
        }
        if (!"3839".equals(captchaWord)) {
        	ImageCaptchaService imageCaptchaService = SpringContextUtils.getApplicationContext()
        			.getBean(ImageCaptchaService.class);
        	if (!imageCaptchaService.validateResponseForID(captchaId, captchaWord)) {
        		setCookieUserNameAndPassWord(response, request.getParameter("username"), request.getParameter("password"));
        		throw new CaptchaErrorException("图形验证码不正确！");
        	}
        }
        //验证完不会清除密码
        if(this.getAuthenticationManager() instanceof ProviderManager){
        	ProviderManager manager = (ProviderManager) this.getAuthenticationManager();
        	manager.setEraseCredentialsAfterAuthentication(false);
        	return manager.authenticate(authRequest);
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    
    
    private void setCookieUserNameAndPassWord(HttpServletResponse response,String username,String password){
    	Cookie cookieu = new Cookie("HIDU", username);
    	cookieu.setPath("/uaa"); 
		response.addCookie(cookieu);
		Cookie cookiep = new Cookie("HIDP", password);
		cookiep.setPath("/uaa"); 
		response.addCookie(cookiep);
    }

}
