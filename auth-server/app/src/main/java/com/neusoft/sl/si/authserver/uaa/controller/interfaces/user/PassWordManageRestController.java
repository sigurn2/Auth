package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.security.password.mobile.Des3Tools;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.*;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;

import io.swagger.annotations.ApiOperation;
/**
 * 密码控制器
 *
 * @author mojf
 */
@RestController
@RequestMapping("/ws/password")
public class PassWordManageRestController {
	/**
	 * 日志
	 */
	private static Logger log = LoggerFactory.getLogger(PassWordManageRestController.class);

	@Autowired
	private UserCustomService userCustomService;
	@Resource
	private UserService userService;

	@Autowired
	private CaptchaRequestService captchaRequestService;

	//@Autowired
	//private ResetPasswordUserRepository resetPasswordUserRepository;
	@Value("${saber.auth.zwfw.app.url}")
	private String zwfwAppUrl;
	@Value("${saber.http.proxy.host}")
	private String host = "";

	@Value("${saber.http.proxy.port}")
	private String port = "";

	/**
	 * 重置密码
	 *
	 * @param passWordResetDetailDTO
	 */
	@ApiOperation(value = "PUT个人重置密码", tags = "密码操作接口", notes = "重置密码PassWordManageRestController，校验短信验证码")
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void reset(@RequestBody PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request) {
		log.debug("重置密码修改DTO，mobile = {}username={}", passWordResetDetailDTO.getMobilenumber(), passWordResetDetailDTO.getUsername());

		try {
			resetPwd(passWordResetDetailDTO);
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
		userCustomService.resetPassword(passWordResetDetailDTO, request);
	}

	/**
	 * App修改密码
	 *
	 * @param passWordResetDetailDTO
	 */
	@ApiOperation(value = "PUT个人修改密码", tags = "密码操作接口", notes = "重置密码PassWordManageRestController，校验短信验证码")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void update(@RequestBody UpdatePwdDTO passWordResetDetailDTO, HttpServletRequest request) {
		log.debug("修改密码DTO,账户={}旧密码 = {}新密码={}",passWordResetDetailDTO.getAccount(),passWordResetDetailDTO.getOldPassword(), passWordResetDetailDTO.getNewPassword());

		try {
			updatePwd(passWordResetDetailDTO);
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
		userService.updatePassWord(passWordResetDetailDTO.getAccount(),passWordResetDetailDTO.getOldPassword(), passWordResetDetailDTO.getNewPassword());
	}

	/**
	 * App忘记密码
	 *
	 * @param passWordResetDetailDTO
	 */
	@ApiOperation(value = "App忘记密码", tags = "密码操作接口", notes = "重置密码PassWordManageRestController，校验短信验证码")
	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void forget(@RequestBody ForgetPwdDTO passWordResetDetailDTO, HttpServletRequest request) {
		log.debug("修改密码DTO,账户={}新密码={}",passWordResetDetailDTO.getIdNumber(), passWordResetDetailDTO.getNewPassword());
		//身份证获取手机号
		String mobile=idNumberGetMobile(passWordResetDetailDTO.getIdNumber());
		// 校验短信验证码
	String sms = captchaRequestService.smsCaptchaRequest(mobile, request);
	if (!"".equals(sms)) {
		throw new CaptchaErrorException(sms);
	}
		try {
			idNumberforgetPwd(passWordResetDetailDTO);
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}
	//	userService.forgetPassWord(passWordResetDetailDTO.getIdNumber(), passWordResetDetailDTO.getNewPassword());
	}


	@ApiOperation(value = "PUT专家用户重置密码", tags = "密码操作接口", notes = "专家用户重置密码PassWordManageRestController，校验短信验证码")
	@RequestMapping(value = "/reset/expert", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void resetForExpert(@RequestBody PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request) {
		log.debug("专家用户重置密码修改DTO，idNumber={}", passWordResetDetailDTO.getIdNumber());
		userCustomService.resetPasswordForExpert(passWordResetDetailDTO, request);
	}

	@PutMapping(value = "/reset/face")
	@ResponseStatus(HttpStatus.CREATED)
	public void resetForFace(HttpServletRequest request) {
		userCustomService.resetPasswordForFace(request);
	}

	public void resetPwd(PassWordResetDetailDTO dto) throws Exception {
		ZwfwResetPasswordDTO resetPasswordDTO = new ZwfwResetPasswordDTO(dto.getUsername(), dto.getMobilenumber(), DemoDesUtil.encPwd(dto.getNewPassword()));
//		String checkRequest = DemoDesUtil.encrypt("{\"score\":\"1\",\"password\":\"" +dto.getPassword() +"\",\"credittype\":\"10\",\"creditId\":\""+dto.getIdNumber()+"\",\"method\":\"register\",\""+dto.getIdNumber()+"\",\"username\":\""+ dto.getAccount() +"\",\"service\":\"check\",\"truename\":\""+ dto.getName() + "\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
		String resetRequest = DemoDesUtil.encrypt(JSONObject.toJSONString(resetPasswordDTO), DemoDesUtil.getDtKey());
		//log.debug("checkReuqest = {}, type = {},value  = {}",checkRequest, type,param );
		JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, resetRequest, host, port), DemoDesUtil.getDtKey()));
		//JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToAppNoProxy(zwfwAppUrl, resetRequest), DemoDesUtil.getDtKey()));

		String code = json.get("code").toString();
		String msg = json.get("msg").toString();
//		String msg = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, registerRequest), DemoDesUtil.getDtKey())).get("msg").toString();
//		JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, registerRequest), DemoDesUtil.getDtKey())).get("result").toString();
		//if (resultDTO.getData())
		if (!"0000".equals(code)) {

			throw new BadCredentialsException("重置失败:" + msg);
		}
	}
	public void updatePwd(UpdatePwdDTO dto) throws Exception {
		String request = null;
		try{
			if (dto.getAccount().length()==18 || dto.getAccount().length()==11){
				//身份证号登录或者手机号登录
				request = DemoDesUtil.encrypt("{\"score\":\"1\",\"username\":\"" + Des3Tools.encode(dto.getAccount()) + "\",\"password\":\"" + DemoDesUtil.encPwd(dto.getOldPassword()) + "\",\"method\":\"loginnp\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}", DemoDesUtil.getDtKey());

			} else {
				request = DemoDesUtil.encrypt("{\"score\":\"1\",\"username\":\"" + dto.getAccount() + "\",\"password\":\"" + DemoDesUtil.encPwd(dto.getOldPassword()) + "\",\"method\":\"loginnp\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}", DemoDesUtil.getDtKey());
			}

		} catch (Exception e) {
			throw new BadCredentialsException("加解密错误");
		}
		log.debug("================调取政务网获取用户信息request ={}========", request);
		String encryptUser = HttpClientTools.httpPostToApp(zwfwAppUrl, request, host, port);

		JSONObject loginjson = null;

		try {
			loginjson = JSONObject.parseObject(DemoDesUtil.decrypt(encryptUser, DemoDesUtil.getDtKey()));
		} catch (Exception e) {
			throw new BadCredentialsException("加解密错误");
		}

		// 当用户名是11/18位的时候

		if (loginjson.get("msg").toString().equals("登录信息错误")){

			try {
				request = DemoDesUtil.encrypt("{\"score\":\"1\",\"username\":\"" + dto.getAccount() + "\",\"password\":\"" + DemoDesUtil.encPwd(dto.getOldPassword()) + "\",\"method\":\"loginnp\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}", DemoDesUtil.getDtKey());
			} catch (Exception e) {
				throw new BadCredentialsException("加解密错误");
			}

			log.debug("================调取政务网获取用户信息request ={}========", request);
			String encryptUser1 = HttpClientTools.httpPostToApp(zwfwAppUrl, request, host, port);
			try {
				loginjson = JSONObject.parseObject(DemoDesUtil.decrypt(encryptUser1, DemoDesUtil.getDtKey()));
			} catch (Exception e) {
				throw new BadCredentialsException("加解密错误");
			}
		}

		JSONObject reljson= loginjson.getJSONObject("result");

		String token= reljson.getString("token");

		ZwfwUpdatePwdDTO zwfwUpdatePwdDTO = new ZwfwUpdatePwdDTO(DemoDesUtil.encPwd(dto.getOldPassword()), DemoDesUtil.encPwd(dto.getNewPassword()),token);
		String resetRequest = DemoDesUtil.encrypt(JSONObject.toJSONString(zwfwUpdatePwdDTO), DemoDesUtil.getDtKey());
		JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, resetRequest, host, port), DemoDesUtil.getDtKey()));

		String code = json.get("code").toString();
		String msg = json.get("msg").toString();

		if (!"0000".equals(code)) {

			throw new BadCredentialsException("修改密码失败:" + msg);
		}
		else {
			log.debug("修改密码成功！");
		}
	}


	public String idNumberGetMobile(String idNumber){
		String checkRequest="";
		String mobile="";
		try {
			checkRequest = DemoDesUtil.encrypt("{\"score\":\"1\",\"cardcode\":\"" + idNumber + "\",\"method\":\"sendphone\",\"service\":\"wechat\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
            mobile= Des3Tools.decode(JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, checkRequest,host,port), DemoDesUtil.getDtKey())).get("result").toString());
		} catch (Exception e) {
			throw new BadCredentialsException("加解密错误");
		}

		return mobile;

	}

	public  void  idNumberforgetPwd(ForgetPwdDTO dto) throws Exception {
		String forgetRequest="";
		JSONObject json= null;

		//身份证获取手机号
		String mobile=idNumberGetMobile(dto.getIdNumber());
		//手机号获取账户名
		ZwfwPhoneDTO zwfwPhoneDTO = new ZwfwPhoneDTO(mobile);
		String resetRequest = DemoDesUtil.encrypt(JSONObject.toJSONString(zwfwPhoneDTO), DemoDesUtil.getDtKey());
		JSONObject getAccount = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, resetRequest, host, port), DemoDesUtil.getDtKey()));
		String account =getAccount.get("result").toString();
		String password = DigestUtils.md5DigestAsHex(dto.getNewPassword().getBytes());
		forgetRequest = DemoDesUtil.encrypt("{\"score\":\"1\",  \"userName\":\"" + account + "\",   \"mobile\":\"" + mobile + "\",\"password\":\"" + password + "\",     \"method\":\"resetPwd\",\"service\":\"user\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}", DemoDesUtil.getDtKey());

		try {
			json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, forgetRequest, host, port), DemoDesUtil.getDtKey()));

		} catch (Exception e) {
			throw new BadCredentialsException("加解密错误");
		}

		String code = json.get("code").toString();
		String msg = json.get("msg").toString();
		if (!"0000".equals(code)) {

			throw new BadCredentialsException("重置密码失败:" + msg);
		}
		else {
			log.debug("重置密码成功！");
		}

	}















}
