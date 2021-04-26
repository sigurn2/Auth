package com.neusoft.ehrss.liaoning.security.zwfw.person;

import com.neusoft.ehrss.liaoning.provider.ecard.EcardService;
import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.ehrss.liaoning.security.UserLoginUrlAuthenticationEntryPoint;
import com.neusoft.ehrss.liaoning.security.person.QRCodeAuthenticationProvider;
import com.neusoft.ehrss.liaoning.security.person.QRCodeAuthenticationSuccessHandler;
import com.neusoft.ehrss.liaoning.security.person.QRCodeRequestProcessingFilter;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.userdetails.person.PersonUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.Filter;
@Configuration
@Order(-40)
public class ZwfwPersonSecurityConfiguration extends AbstractSecurityConfiguration {

    @Autowired
    private ZwfwPersonUserDetailService userDetailsService;

    @Autowired
    private UserCustomService userCustomService;

    @Value("${saber.auth.person.url}")
    private String personUrl = "";

    @Value("${saber.auth.zwfw.idsUrl}")
    private String baseUrl = "";
    
    @Value("${saber.auth.zwfw.redirectURL}")
    private String redirectUrl = "";

    @Value("${saber.auth.zwfw.returnPersonUrl}")
    private  String returnurl;



    @Value("${saber.http.proxy.host}")
    private  String host ="" ;


    @Value("${saber.http.proxy.port}")
    private  String port ="";

    @Value("${saber.auth.zwfw.coAppName}")
    private   String coAppName;

    @Override
    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    protected String getFilterProcessesUrl() {
        return "/api/zwfw/person/*";
    }

    @Override
    protected String getTargetUrl() {
        return personUrl;
    }

    @Override
    protected String getLoginFormUrl() {
        return "/personlogin/";
    }

    @Override
    protected String[] getAntMatchers() {
        return new String[] { "/api/zwfw/person/*" };
    }

    @Override
    protected AuthenticationProvider getAuthenticationProvider() {
        QRCodeAuthenticationProvider provider = new QRCodeAuthenticationProvider();
        provider.setUserDetailsService(getUserDetailsService());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    protected Filter getAuthenticationProcessingFilter() throws Exception {
        ZwfwPersonLoginProcessFilter filter = new ZwfwPersonLoginProcessFilter(getFilterProcessesUrl());
        filter.setAuthenticationManager(authenticationManager());
        filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
        filter.setAuthenticationSuccessHandler(getSuccessHandler());
        filter.setAuthenticationFailureHandler(getFailureHandler());
        filter.setUserService(userCustomService);
        filter.setBaseUrl(baseUrl);
        filter.setReturnUrl(returnurl);
        filter.setHost(host);
        filter.setPort(port);
        filter.setRedirectUrl(redirectUrl); 
        filter.setCoAppName(coAppName); 
        return filter;
    }

    @Override
    protected SimpleUrlAuthenticationFailureHandler getFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/error");
    }

    @Override
    protected SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
        QRCodeAuthenticationSuccessHandler successHandler = new QRCodeAuthenticationSuccessHandler(getTargetUrl());
        successHandler.setAlwaysUseDefaultTargetUrl(false);
        return successHandler;
    }

}
