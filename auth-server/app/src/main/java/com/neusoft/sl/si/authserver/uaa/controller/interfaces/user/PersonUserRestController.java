package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.util.DesHelp;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.ehrss.liaoning.utils.HttpResultDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.*;
import org.neo4j.cypher.internal.compiler.v2_1.functions.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;

/**
 * 个人用户控制器
 *
 * @author mojf
 *
 */
@RestController
@RequestMapping
public class PersonUserRestController {

	/** 日志 */
	private static final Logger log = LoggerFactory.getLogger(PersonUserRestController.class);

	@Autowired
	private UserCustomService userCustomService;
	@Autowired
	private CaptchaRequestService captchaRequestService;
	@Value("${saber.auth.zwfw.app.url}")
	private String zwfwAppUrl;
	@Value("${saber.http.proxy.host}")
	private    String host ="" ;


	@Value("${saber.http.proxy.port}")
	private    String port ="";
	@ApiOperation(value = "POST创建个人用户", tags = "个人用户操作接口", notes = "创建个人用户", response = PersonUserDTO.class)
	@RequestMapping(method = RequestMethod.POST, value = "/ws/user/person/{type}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createPersonUserMobile(@PathVariable String type, @RequestBody PersonUserDTO userDTO, HttpServletRequest request) throws Exception {
		log.debug("个人用户登记DTO,userDTO={}", userDTO.getIdNumber());
//		// 校验手机验证码
//		String result = captchaRequestService.smsCaptchaRequest(request);
//		if (!"".equals(result)) {
//			throw new CaptchaErrorException(result);
//		}
		// 如果account是空，就生成一个UUID
		if (StringUtils.isEmpty(userDTO.getAccount())) {
			throw new BadCredentialsException("用户名不能为空");
		}

		String username = userDTO.getIdNumber();
		String password = userDTO.getPassword();
		try {
			password = DesHelp.decrypt(password.replaceAll(" ", "+"), "OqcbHRPGWILe43usueyHZyYT");
			username = DesHelp.decrypt(username.replaceAll(" ", "+"), "OqcbHRPGWILe43usueyHZyYT");
		} catch (Exception e) {
			throw new RuntimeException("账号加解密失败");
		}
		userDTO.setIdNumber(username);
		userDTO.setPassword(password);
		//校验关键信息唯一性
		validate("creditid", userDTO.getIdNumber());
		validate("username", userDTO.getAccount());
		validate("mobile", userDTO.getMobilenumber());
		try {
			register(userDTO);
		}catch (Exception e) {
			throw new BadCredentialsException("注册失败"+ e.getMessage());
		}

		User user = PersonUserDTOAssembler.crtfromDTO(userDTO);
		//user.setMobile(request.getHeader("mobilenumber"));
		String clientType = "";
		// 网厅
		if ("web".equals(type)) {
			user.setExtension("web_register");
			clientType = ClientType.PC.toString();
		} else if ("wx".equals(type)) {// 微信
			user.setExtension("weixin_register");
			clientType = ClientType.WeChat.toString();
		} else if ("mobile".equals(type)) {// 手机APP
			user.setExtension("mobile_register");
			clientType = ClientType.Mobile.toString();
		} else {
			throw new ResourceNotFoundException("访问资源有误");
		}
		// 保存user对象
		user = userCustomService.createPerson(user);
		// 记录日志
		UserLogManager.saveRegisterLog(SystemType.Person.toString(), clientType, user, request);
	}

	@ApiOperation(value = "POST校验个人用户是否注册-一体机", tags = "个人用户操作接口", notes = "校验个人用户是否注册，该请求需要客户端授权")
	@RequestMapping(method = RequestMethod.GET, value = "/atm/user/person/{mobilenumber}")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
	public AtmVerifyResultDTO verifyPersonUserATM(@PathVariable("mobilenumber") String mobilenumber, @RequestParam("cardNumber") String cardNumber, HttpServletRequest request) {
		log.debug("一体机个人用户校验是否注册mobilenumber={},cardNumber={}", mobilenumber, cardNumber);
		return userCustomService.verifyPersonUserATM(mobilenumber, cardNumber);
	}

	@ApiOperation(value = "POST创建个人用户-一体机", tags = "个人用户操作接口", notes = "创建个人用户，该请求需要客户端授权")
	@RequestMapping(method = RequestMethod.POST, value = "/atm/user/person")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
	public PersonUserDTO createPersonUserATM(@RequestBody PersonUserDTO userDTO, HttpServletRequest request) {
		log.debug("一体机个人用户登记DTO,userDTO={}", userDTO.getIdNumber());
		// 如果account是空，就生成一个UUID
		if (null == userDTO.getAccount() || "".equals(userDTO.getAccount())) {
			userDTO.setAccount(UuidUtils.getRandomUUIDString());
		}
		User user = PersonUserDTOAssembler.crtfromDTO(userDTO);
		user.setExtension("atm_register");
		// 保存user对象
		user = userCustomService.createPersonUserATM(user);
		// 记录日志
		UserLogManager.saveRegisterLog(SystemType.Person.toString(), ClientType.ATM.toString(), user, request);
		return PersonUserDTOAssembler.toDTO(user);
	}

	public void validate(String type, String param) throws Exception {

		String checkRequest = DemoDesUtil.encrypt("{\"score\":\"1\",\"field\":\"" + type + "\",\"method\":\"param\",\"service\":\"check\",\"value\":\""+ param + "\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
		log.debug("checkReuqest = {}, type = {},value  = {}",checkRequest, type,param );
		String code =  JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, checkRequest,host,port), DemoDesUtil.getDtKey())).get("code").toString();
		String msg = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, checkRequest,host,port), DemoDesUtil.getDtKey())).get("msg").toString();
		Boolean checkFlag = Boolean.parseBoolean(JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, checkRequest,host,port), DemoDesUtil.getDtKey())).get("result").toString());
		//if (resultDTO.getData())
		if (!"0000".equals(code)){
			throw new BadCredentialsException("接口校验失败"+ msg);
		}else {
			if (!checkFlag){
				switch (type){
					case "creditid":throw new BadCredentialsException("身份证号已存在，请核对后重新注册");
					case "mobile":throw new BadCredentialsException("手机号已存在，请核对后重新注册");
					case "username":throw new BadCredentialsException("用户名已存在，请核对后重新注册");
					default :throw new BadCredentialsException("唯一性校验参数错误");
				}


			}
		}
	}

	public void register( PersonUserDTO dto) throws Exception {

		//实名认证
		RealAuthDTO realAuthDTO=new RealAuthDTO(dto.getIdNumber(),dto.getName());
		String realAuthRequest= DemoDesUtil.encrypt(JSONObject.toJSONString(realAuthDTO),DemoDesUtil.getDtKey());
		JSONObject realAuthjson = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, realAuthRequest,host,port), DemoDesUtil.getDtKey()));
		String realBoolean = realAuthjson.get("result").toString();

		if (!"true".equals(realBoolean)){
			throw new BadCredentialsException("注册失败,实名验证未通过，请检查您输入的姓名，身份证是否无误");
		}

		//注册
		ZwfwRegisterDTO registerDTO = new ZwfwRegisterDTO(dto.getAccount(),dto.getIdNumber(),dto.getName(),dto.getMobilenumber(),DemoDesUtil.encPwd(dto.getPassword()));
		String registerRequest = DemoDesUtil.encrypt(JSONObject.toJSONString(registerDTO),DemoDesUtil.getDtKey());
		JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, registerRequest,host,port), DemoDesUtil.getDtKey()));
		String msg = json.get("msg").toString();
		String code = json.get("code").toString();

		if (!"0000".equals(code)){
			throw new BadCredentialsException("注册失败" + msg);
		}

	}

}
