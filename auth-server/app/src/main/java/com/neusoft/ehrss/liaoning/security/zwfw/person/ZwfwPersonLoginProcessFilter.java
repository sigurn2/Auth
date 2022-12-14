package com.neusoft.ehrss.liaoning.security.zwfw.person;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.neusoft.ehrss.liaoning.config.channel.ChannelConfiguration;
import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.provider.ecard.EcardService;
import com.neusoft.ehrss.liaoning.provider.ecard.response.EcardCardResponse;
import com.neusoft.ehrss.liaoning.security.password.idnumbername.RedisService;
import com.neusoft.ehrss.liaoning.security.person.QRCodeRequestProcessingFilter;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.AESUtils;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.SignUtil;
import com.neusoft.ehrss.liaoning.security.zwfw.dto.ZwfwUserDTO;
import com.neusoft.ehrss.liaoning.utils.Base64Tools;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.password.CheckPwdService;
import com.trs.Des3Tools;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-02-17 14:24
 **/

public class ZwfwPersonLoginProcessFilter extends AbstractAuthenticationProcessingFilter {

//    private static final String coAppName="cysrst";

//    @Value("${saber.auth.zwfw.coAppName}")
    private   String coAppName;
    
    private static final Logger log = LoggerFactory.getLogger(QRCodeRequestProcessingFilter.class);

    private UserCustomService userService;

    private String baseUrl;
    
    private String redirectUrl ;

    private String returnUrl;

    private String host ;

    private String port ;

    public ZwfwPersonLoginProcessFilter(String filterProcessesUrl) {
        super(filterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        HttpSession httpSession = request.getSession();
        String gSessionId= request.getParameter("com.trs.idm.gSessionId");
        String trsidsssosessionid =  request.getParameter("trsidsssosessionid");
        String Sid = request.getParameter("sid");
        String ssoSessionId = org.apache.commons.lang3.StringUtils.isBlank(gSessionId)?trsidsssosessionid:gSessionId;
        httpSession.setAttribute("ssoSessionId",ssoSessionId);
        String httpSessionid=httpSession.getId();
        String httpclienturl=baseUrl+"service?idsServiceType=httpssoservice&serviceName=findUserBySSOID&coAppName="+coAppName+"&ssoSessionId="+ssoSessionId+"&coSessionId="+httpSessionid+"&type=json";

        log.debug("clienturl; = {} ",httpclienturl);
        JSONObject jsonObject = HttpClientTools.httpGet(httpclienturl,host,port);
        ZwfwUserDTO userDTO = new ZwfwUserDTO();
        String loginurl=redirectUrl+"custom/liaoning/login.jsp?coAppName="+Base64Tools.encode(coAppName.getBytes())+"&coSessionId="+httpSessionid+"&gSessionId="+ssoSessionId+"&surl="+Base64Tools.encode(returnUrl.getBytes());
        if("200".equals(jsonObject.get("code").toString())){
            System.out.println("????????????");

            //String userData = JSONObject.parseObject(jsonObject.get("data").toString()).toJSONString();
            userDTO = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(jsonObject.get("data").toString()).toJSONString()).get("user").toString(),ZwfwUserDTO.class);
            log.debug("userDTO = {},???????????? = {}", userDTO,userDTO.getRealnameAuth());
            if ("2".equals(userDTO.getScope())){
                throw new BadCredentialsException("?????????????????????????????????????????????????????????????????????????????????????????????");
            }
            if (!"1".equals(userDTO.getRealnameAuth()) && !"2".equals(userDTO.getRealnameAuth())){
                throw new BadCredentialsException("?????????????????????????????????");
            }
        }else if("404".equals(jsonObject.get("code")+"")){
            response.sendRedirect(loginurl);
            return null;
        }else if("500".equals(jsonObject.get("code"))){
            response.sendRedirect(loginurl);
            System.out.println("error");
        }
        String username = Des3Tools.decode(userDTO.getIdNumber());
        String password = userDTO.getName();
        String mobile = Des3Tools.decode(userDTO.getMobile());
        String account = userDTO.getUsername();
        String uuid = userDTO.getUuid();

        //2021.09.24 ?????? ???????????????????????????????????????????????????
        if (Sid !=null){
            RedisService redisService = SpringContextUtils.getBean("RedisService");
            log.debug("sid????????????????????????????????????sid={}",Sid);
           if (redisService.hasKey("SID:"+username))
           {
               redisService.delete("SID:"+username);
           }
            redisService.set("SID:" + username, Sid, 5, TimeUnit.MINUTES);

            log.debug("Redis?????????sid={}",redisService.get("SID:"+username));
        }


        log.debug("username = {}, name = {}, mobile = {}, account = {},uuid={}",username,password, mobile,account,uuid);
        //request.getSession()
      if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
         throw new BadCredentialsException("????????????????????????????????????????????????");
      }

        username = username + "@@" + password + "@@" + mobile + "@@" + account+"@@" + uuid;
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
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

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setCoAppName(String coAppName) {
		this.coAppName = coAppName;
	}
    
}

