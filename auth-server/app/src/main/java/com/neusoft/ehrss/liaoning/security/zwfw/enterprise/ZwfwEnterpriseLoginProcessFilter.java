package com.neusoft.ehrss.liaoning.security.zwfw.enterprise;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.security.password.idnumbername.RedisService;
import com.neusoft.ehrss.liaoning.security.person.QRCodeRequestProcessingFilter;
import com.neusoft.ehrss.liaoning.security.zwfw.dto.ZwfwUserDTO;
import com.neusoft.ehrss.liaoning.utils.Base64Tools;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.trs.Des3Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: liaoning-auth
 * @description: 企业登录处理器
 * @author: lgy
 * @create: 2020-02-17 14:24
 **/

public class ZwfwEnterpriseLoginProcessFilter extends AbstractAuthenticationProcessingFilter {

//    private static final String coAppName = "cysrst";
    private   String coAppName;

    private static final Logger log = LoggerFactory.getLogger(QRCodeRequestProcessingFilter.class);

    private UserCustomService userService;

    @Autowired
    RedisService redisService;

    private String baseUrl;

    private String redirectUrl ;
    private String returnUrl;

    private String host ;


    private String port ;


    public ZwfwEnterpriseLoginProcessFilter(String filterProcessesUrl) {
        super(filterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        HttpSession httpSession = request.getSession();
        String gSessionId = request.getParameter("com.trs.idm.gSessionId");
        String trsidsssosessionid = request.getParameter("trsidsssosessionid");
        String ssoSessionId = org.apache.commons.lang3.StringUtils.isBlank(gSessionId) ? trsidsssosessionid : gSessionId;
        httpSession.setAttribute("ssoSessionId", ssoSessionId);
        String Sid = request.getParameter("sid");
        String httpSessionid = httpSession.getId();
        String httpclienturl = baseUrl + "service?idsServiceType=httpssoservice&serviceName=findUserBySSOID&coAppName=" + coAppName + "&ssoSessionId=" + ssoSessionId + "&coSessionId=" + httpSessionid + "&type=json";
        String username = "";
        String mobile = "";
        String password = "";
        String uuid = "";
        String profession = "";
        log.debug("clienturl; = {} ", httpclienturl);
        JSONObject jsonObject = HttpClientTools.httpGet(httpclienturl,host,port);
        ZwfwUserDTO userDTO = new ZwfwUserDTO();
        String loginurl = redirectUrl + "custom/liaoning/login.jsp?coAppName=" + Base64Tools.encode(coAppName.getBytes()) + "&coSessionId=" + httpSessionid + "&gSessionId=" + ssoSessionId + "&surl=" + Base64Tools.encode(returnUrl.getBytes());
        if ("200".equals(jsonObject.get("code").toString())) {
            System.out.println("认证成功");
            //String userData = JSONObject.parseObject(jsonObject.get("data").toString()).toJSONString();
            userDTO = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(jsonObject.get("data").toString()).toJSONString()).get("user").toString(), ZwfwUserDTO.class);
            log.debug("userDTO = {}", userDTO);
            if (!"1".equals(userDTO.getCorpRealnameAuth())){
                throw new BadCredentialsException("该用户未实名，无法登陆");
            }
        } else if ("404".equals(jsonObject.get("code") + "")) {
            response.sendRedirect(loginurl);
            return null;
        } else if ("500".equals(jsonObject.get("code"))) {
            response.sendRedirect(loginurl);
            System.out.println("error");
        }
        try {
             username = Des3Tools.decode(userDTO.getJbrIdNumber());
             password = userDTO.getCompanyName();
             mobile = Des3Tools.decode(userDTO.getJbrMobile());
             uuid = userDTO.getUuid();
             profession = Des3Tools.decode(userDTO.getProfession());
        } catch (Exception e) {
            throw new BadCredentialsException("加解密错误");
        }

        //2021.09.24 更新 新增营商局点击模块跳转网厅对应模块

        if (Sid !=null){
            RedisService redisService = SpringContextUtils.getBean("RedisService");
            log.debug("sid不为空，申报业务跳转对应sid={}",Sid);
            if (redisService.hasKey("SID:"+profession))
            {
                redisService.delete("SID:"+profession);
            }
            redisService.set("SID:" + profession, Sid, 5, TimeUnit.MINUTES);

            log.debug("Redis里存的sid={}",redisService.get("SID:"+profession));
        }

        log.debug("username = {}, name = {}, mobile = {} profession = {},uuid={}", username, password, mobile, profession,uuid);
        //request.getSession()
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(profession)) {
            throw new BadCredentialsException("查询政务网关键信息为空，无法登陆");
        }

        username = profession + "@@" + password + "@@" + mobile +"@@" + username +"@@" + uuid;
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUserService(UserCustomService userService) {
        this.userService = userService;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setCoAppName(String coAppName) {
		this.coAppName = coAppName;
	}

}

