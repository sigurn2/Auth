package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import com.neusoft.ehrss.liaoning.utils.HttpResultDTO;
import com.neusoft.ehrss.liaoning.utils.MD5Tools;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.ZwfwRegisterDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.ZwfwResetPasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PassWordResetDetailDTO;

import io.swagger.annotations.ApiOperation;

/**
 * 密码控制器
 * 
 * @author mojf
 *
 */
@RestController
@RequestMapping("/ws/password")
public class PassWordManageRestController {

	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(PassWordManageRestController.class);

	@Autowired
	private UserCustomService userCustomService;
	@Value("${saber.auth.zwfw.app.url}")
	private String zwfwAppUrl;
	@Value("${saber.http.proxy.host}")
	private    String host ="" ;


	@Value("${saber.http.proxy.port}")
	private    String port ="";
	/**
	 * 重置密码
	 * 
	 * @param passWordResetDetailDTO
	 */
	@ApiOperation(value = "PUT个人重置密码", tags = "密码操作接口", notes = "重置密码PassWordManageRestController，校验短信验证码")
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void reset(@RequestBody PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request) {
		log.debug("重置密码修改DTO，mobile = {}username={}",  passWordResetDetailDTO.getMobilenumber(),passWordResetDetailDTO.getUsername());

		try{
			resetPwd(passWordResetDetailDTO);
		}catch (Exception e ){
			throw new BadCredentialsException(e.getMessage());
		}
		userCustomService.resetPassword(passWordResetDetailDTO, request);
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

	public void resetPwd( PassWordResetDetailDTO dto) throws Exception {
		ZwfwResetPasswordDTO resetPasswordDTO = new ZwfwResetPasswordDTO(dto.getUsername(),dto.getMobilenumber(), DemoDesUtil.encPwd(dto.getNewPassword()));
//		String checkRequest = DemoDesUtil.encrypt("{\"score\":\"1\",\"password\":\"" +dto.getPassword() +"\",\"credittype\":\"10\",\"creditId\":\""+dto.getIdNumber()+"\",\"method\":\"register\",\""+dto.getIdNumber()+"\",\"username\":\""+ dto.getAccount() +"\",\"service\":\"check\",\"truename\":\""+ dto.getName() + "\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
		String resetRequest = DemoDesUtil.encrypt(JSONObject.toJSONString(resetPasswordDTO),DemoDesUtil.getDtKey());
		//log.debug("checkReuqest = {}, type = {},value  = {}",checkRequest, type,param );
		JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, resetRequest,host,port), DemoDesUtil.getDtKey()));
		//JSONObject json = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToAppNoProxy(zwfwAppUrl, resetRequest), DemoDesUtil.getDtKey()));

		String code = json.get("code").toString();
		String msg = json.get("msg").toString();
//		String msg = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, registerRequest), DemoDesUtil.getDtKey())).get("msg").toString();
//		JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, registerRequest), DemoDesUtil.getDtKey())).get("result").toString();
		//if (resultDTO.getData())
		if (!"0000".equals(code)){

			throw new BadCredentialsException("重置失败:" + msg);
		}
	}


}
