/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.oauth2.provider.password;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.neusoft.ehrss.liaoning.util.DesHelp;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.security.password.atm.AtmAuthenticationToken;
import com.neusoft.ehrss.liaoning.security.password.ecard.EcardGatewayAuthenticationToken;
import com.neusoft.ehrss.liaoning.security.password.idnumbername.IdNumberNameAuthenticationToken;
import com.neusoft.ehrss.liaoning.security.password.mobile.company.RlzyscCompanyAuthenticationToken;
import com.neusoft.ehrss.liaoning.security.password.mobile.pattern.MobilePatternAuthenticationToken;
import com.neusoft.ehrss.liaoning.security.password.mobile.person.RlzyscPersonAuthenticationToken;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * @author Dave Syer
 */
public class ResourceOwnerPasswordTokenGranter extends AbstractTokenGranter {

    private static final Logger log = LoggerFactory.getLogger(ResourceOwnerPasswordTokenGranter.class);

    private static final String GRANT_TYPE = "password";

    private final AuthenticationManager authenticationManager;

    public ResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                             OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected ResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                                OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");
        Authentication userAuth = null;

        String captchId = parameters.get("captchaId");
        String captchWord = parameters.get("captchaWord");

        String loginType = parameters.get("loginType");
        if (StringUtils.isEmpty(loginType) || "mobile".equals(loginType)) {

            try {
                password = DesHelp.decrypt(password.replaceAll(" ", "+"), "OqcbHRPGWILe43usueyHZyYT");
                username = DesHelp.decrypt(username.replaceAll(" ", "+"), "OqcbHRPGWILe43usueyHZyYT");
            } catch (Exception e) {
                throw new RuntimeException("账号加解密失败");
            }


            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("请输入用户名");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("请输入密码");
            }
            userAuth = new UsernamePasswordAuthenticationToken(username + "@@" + password + "@@" + captchId + "@@" + captchWord, password);
        } else if ("pattern".equals(loginType)) {
            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("请输入用户名");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("手势密码为空");
            }
            userAuth = new MobilePatternAuthenticationToken(username, password, parameters.get("deviceId"), parameters.get("appType"));
        } else if ("nopwd".equals(loginType)) {

            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("请输入用户名");
            }
            password = parameters.get("name");
            if (StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("姓名为空");
            }
            try {
                password = URLDecoder.decode(password, "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("解码失败name={}", password, e);
            }
            userAuth = new IdNumberNameAuthenticationToken(username+"@@"+parameters.get("name")+"@@"+parameters.get("requestId"), password);
        } else if ("ecard".equals(loginType)) {
            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("请输入用户名");
            }
            userAuth = new EcardGatewayAuthenticationToken(username, password);
        } else if ("atm".equals(loginType) && "acme_atm".equals(client.getClientId())) {
            username = parameters.get("idNumber");
            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("身份证号为空");
            }
            password = parameters.get("name");
            if (StringUtils.isEmpty(password)) {
                password = "";
//				throw new BadCredentialsException("姓名为空");
            }
            userAuth = new AtmAuthenticationToken(username + "@@" + password, "");
        } else if ("alipay".equals(loginType) && "acme_alipay".equals(client.getClientId())) {
            username = parameters.get("idNumber");
            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("身份证号为空");
            }
            password = parameters.get("name");
            if (StringUtils.isEmpty(password)) {
                password = "";
//				throw new BadCredentialsException("姓名为空");
            }
            userAuth = new IdNumberNameAuthenticationToken(username, password);
        } else if ("nnrlzysc".equals(client.getClientId())) {
            if (StringUtils.isEmpty(username)) {
                throw new BadCredentialsException("请输入用户名");
            }
            if (StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("请输入密码");
            }
            if ("company".equals(loginType)) {
                userAuth = new RlzyscCompanyAuthenticationToken(username, password);
            } else if ("person".equals(loginType)) {
                userAuth = new RlzyscPersonAuthenticationToken(username, password);
            }
        }

        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            // covers expired, locked, disabled cases (mentioned in section 5.2,
            // draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send
            // 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        // 处理登录日志
        Object principal = userAuth.getPrincipal();
        if (principal != null) {
            SaberUserDetails details = (SaberUserDetails) principal;
            // details.setClientType(client.getClientId());
            UserLogonLog log = UserLogManager.userLogonLogSync(details, null);
            details.setLogonlogid(log.getId());// 设置日志id
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

}
