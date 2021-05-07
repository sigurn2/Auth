package com.neusoft.ehrss.liaoning.security.password.idnumbername;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.provider.ecard.request.EcardValidRequest;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.girder.security.oauth2.domain.AuthenticatedCasUser;
import com.neusoft.sl.si.authserver.base.domains.user.PersonalUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static freemarker.template.utility.NullArgumentException.check;

@Service
public class IdNumberNameUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(IdNumberNameUserDetailsService.class);

	@Resource
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	@Autowired
	RedisService redisService;

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	final String PROFIX="REDIS_USER:";

	public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {




		log.debug("================MobileUserDetailsService获取用户信息username={}========", str);
		String[] split = str.split("@@");
		String username = split[0];
		String name = split[1];
		String requestId= split[2];

        //刷脸服务校验
		String httpclienturl="http://10.58.160.44:8081/piles/face/photo/si210500/compare/";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json");
		headers.add("key","bx");
		ResponseEntity<String> entity = restTemplate.exchange(httpclienturl+requestId, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		check(entity.getBody());
		JSONObject object = JSONObject.parseObject(entity.getBody());
		log.debug("object={}",object);
		String result=object.get("result").toString();

			//创建静默用户
		    String account=UUID.randomUUID().toString().substring(0,8);
			User u =new PersonalUser(account,name,"slient");
			u.setExtension("slient");
			u.setIdNumber(username);
			u.setIdType("1");
			u.setMobile("slient");

	        AuthenticatedCasUser casUser = new AuthenticatedCasUser();
	        casUser.setIdNumber(username);
	        casUser.setAccount(account);
	        casUser.setName(name);
	        casUser.setUserType("1");

	    if (redisService.hasKey(PROFIX+u.getAccount())){
	    	redisService.delete(PROFIX+u.getAccount());
	    }

	    redisService.set(PROFIX+u.getAccount(), casUser, 8, TimeUnit.HOURS);


        //用于测试，松姐账户可以直接访问
		if(username.equals("210503198905142145")){

			return new IdNumberNameUserDetails(u);
		}

		switch (result) {
			case "1":
				return new IdNumberNameUserDetails(u);
			case "-10":
				throw new BadCredentialsException("刷脸服务请求超时");
			case "-1":
				throw new BadCredentialsException("刷脸比对不通过");
			case "-99":
				throw new BadCredentialsException("刷脸服务异常，请联系工作人员");
			case "0":
				throw new BadCredentialsException("刷脸服务异常，请联系工作人员");
			default:
				throw new BadCredentialsException("刷脸服务异常，请联系工作人员");

		}




	}

}