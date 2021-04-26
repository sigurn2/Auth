package com.neusoft.sl.si.authserver.uaa.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.uaa.config.security.handler.RestfulLogoutSuccessHandler;
import com.neusoft.sl.si.authserver.uaa.filter.CorsFilter;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseUserDetailService;

/**
 * 安全配置
 * 
 * @author mojf
 *
 */
@Configuration
@Order(-11)
public class DefaultUsernamePasswordSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // private static final int MAX_CONCURRENT_USER_SESSIONS = 1;

    @Autowired
    private EnterpriseUserDetailService userDetailsService;
    @Resource(name = "${saber.auth.password.encoder}")
    private PasswordEncoderService passwordEncoderService;

    @Autowired
    private RestfulLogoutSuccessHandler restfulLogoutSuccessHandler;

    @Value("${saber.auth.cors.allowed.uris}")
    private String corsAllowedUris = "";

    @Value("${saber.auth.cors.allowed.origins}")
    private String corsAllowedOrigins = "";

    @Value("${saber.auth.login.page}")
    private String defaultLoginPage = "/login";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                // 加密算法
                .passwordEncoder(passwordEncoderService.build());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 可匿名访问的服务
        // info -- 系统信息
        // sm -- 发送消息接口
        // card -- 省卡系统查询
        // web.ignoring().antMatchers("/", "/info", "/captcha/**", "/ws/**",
        // "/webservice/**", "/fonts/**", "/views/**",
        // "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**",
        // "/configuration/**", "/card/**",
        // "/right/**", "/calogin/**", "/custom/**", "/druid/**");
        // 国考
        web.ignoring().antMatchers("/", "/info", "/captcha/**", "/ws/**", "/webservice/**", "/fonts/**", "/views/**",
                "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/configuration/**", "/card/**",
                "/right/**", "/calogin/**", "/custom/**", "/druid/**", "/uic/**", "/console/**","/idstools/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
            http.requestMatchers()
                    .antMatchers("/login", "/oauth/authorize", "/logout", "/home",
                            "/ssoSessionId", "/oauth/token/revoke",
                            "/oauth/confirm_access")
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    // login
                    .formLogin()
                    .loginPage(defaultLoginPage)
//                    .successHandler(new RedirectLoginSuccessHandler())
                    .permitAll()
                    // logout config
                    .and()
                    .logout()
                    .logoutSuccessHandler(restfulLogoutSuccessHandler)
//                    // session stateless 这个方向是错误的
//                    .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
//                    .and()
//                    //snipped
//                    .sessionManagement()
//                    .maximumSessions(MAX_CONCURRENT_USER_SESSIONS)
//                    .maxSessionsPreventsLogin(true)
//                    .sessionRegistry(sessionRegistry())
//                    .and()
                    .and()
                    .addFilterBefore(getCorsFilter(),
                            WebAsyncManagerIntegrationFilter.class)
                    .exceptionHandling()
                    .and()
                    // csrf
                    .csrf().disable()
                    .httpBasic().disable();
            // @formatter:on
    }

    // @Bean
    // public SessionRegistry sessionRegistry() {
    // SessionRegistry sessionRegistry = new SessionRegistryImpl();
    // return sessionRegistry;
    // }

    @Bean
    public CorsFilter getCorsFilter() {
        CorsFilter corsFilter = new CorsFilter();
        List<String> allowedUris = new ArrayList<String>(Arrays.asList(corsAllowedUris.split(",")));

        // 授权回调域
        List<String> allowedOrigins = new ArrayList<String>(Arrays.asList(corsAllowedOrigins.split(",")));

        corsFilter.setCorsXhrAllowedUris(allowedUris);
        corsFilter.setCorsXhrAllowedOrigins(allowedOrigins);
        corsFilter.initialize();
        return corsFilter;
    }
}
