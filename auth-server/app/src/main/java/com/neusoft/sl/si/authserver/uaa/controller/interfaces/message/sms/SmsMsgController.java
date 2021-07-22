package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.sms;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.ehrss.liaoning.utils.DemoDesUtil;
import com.neusoft.ehrss.liaoning.utils.Des3Tools;
import com.neusoft.ehrss.liaoning.utils.HttpClientTools;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpert;
import com.neusoft.sl.si.authserver.base.domains.expert.PersonExpertRepository;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.MobileQueryDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.MsgType;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.message.ValidateCaptchaDTO;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.SmsCaptchaDTO;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.exception.ImageCaptchaException;
import com.neusoft.sl.si.authserver.uaa.exception.MessageException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.log.enums.BusinessType;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.authserver.uaa.service.msg.MsgService;

import io.swagger.annotations.ApiOperation;

/**
 * 短信sms控制器
 * 
 * @author zhou.haidong
 *
 */
@RestController
@RequestMapping
public class SmsMsgController {

	private static final Logger log = LoggerFactory.getLogger(SmsMsgController.class);

	@Autowired
	private MsgService msgService;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private CaptchaRequestService captchaRequestService;

	@Autowired
	private PersonExpertRepository personExpertRepository;

	@Value("${saber.auth.zwfw.app.url}")
	private String zwfwAppUrl;
	@Value("${saber.http.proxy.host}")
	private String host = "";


	@Value("${saber.http.proxy.port}")
	private String port = "";

	// @Autowired
	// private ResetPasswordTokenService resetPasswordTokenService;

	/**
	 * 短信验证码发送
	 * 
	 * @param
	 * @return
	 */
	@ApiOperation(value = "POST根据手机号发送短信验证码-手机端", tags = "手机验证码操作接口", notes = "该请求不需要身份证信息。")
	@PostMapping("/captcha/sm/{clientType}/{mobilenumber}")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendSmsCaptchaMobile(@PathVariable("clientType") String clientType, @PathVariable("mobilenumber") String mobilenumber) {
		if ("mobile".equals(clientType)) {// 手机端
			msgService.nextCaptchaPersonSms(mobilenumber, mobilenumber, BusinessType.Default, ClientType.Mobile, "GRWSBS1", "20000");
		} else if ("web".equals(clientType)) {// 网页端
			msgService.nextCaptchaPersonSms(mobilenumber, mobilenumber, BusinessType.Default, ClientType.PC, "GRWSBS1", "20000");
		} else if ("wechat".equals(clientType)) {// 微信
			msgService.nextCaptchaPersonSms(mobilenumber, mobilenumber, BusinessType.Default, ClientType.WeChat, "GRWSBS1", "20000");
		} else {
			log.error("clientType={}有误", clientType);
			throw new MessageException("请求有误");
		}
	}

	@ApiOperation(value = "POST根据身份证号发送短信验证码-手机端", tags = "手机验证码操作接口", notes = "该请求不需要身份证信息。")
	@GetMapping("/captcha/sm/sendbyidNumber")
	@ResponseStatus(HttpStatus.OK)
	public JSONObject sendSmsCaptcha(@RequestParam("idNumber") String idNumber) {
		String checkRequest="";
		String mobile="";
		try {
			checkRequest = DemoDesUtil.encrypt("{\"score\":\"1\",\"cardcode\":\"" + idNumber + "\",\"method\":\"sendphone\",\"service\":\"wechat\",\"version\":\"1.0.0\",\"key\":\"E2A243476964ABAF584C7DFA76A6F949\",\"token\":\"00000000000000000000000000000000\"}",DemoDesUtil.getDtKey());
			String msg = JSONObject.parseObject(DemoDesUtil.decrypt(HttpClientTools.httpPostToApp(zwfwAppUrl, checkRequest,host,port), DemoDesUtil.getDtKey())).get("result").toString();
			mobile= Des3Tools.decode(msg);
		} catch (Exception e) {
			throw new BadCredentialsException("加解密错误");
		}
		//给爷发！
		msgService.nextCaptchaPersonSms(mobile, mobile, BusinessType.Default, ClientType.Mobile, "GRWSBS1", "20000");

           JSONObject post_mobile= new JSONObject();
           post_mobile.put("mobile",mobile);
           return post_mobile;

	}





	@ApiOperation(value = "POST根据手机号发送短信验证码-一体机", tags = "手机验证码操作接口", notes = "该请求需要客户端授权")
	@PostMapping("/atm/captcha/{mobilenumber}")
	@PreAuthorize("hasAnyAuthority('ROLE_CLIENT')")
	public SmsCaptchaDTO sendSmsCaptchaATM(@PathVariable("mobilenumber") String mobilenumber) {
		// 个人网厅账号注册
		return msgService.nextCaptchaNoSave(mobilenumber, mobilenumber, MsgType.Sms, SystemType.Person, UserType.PERSON, BusinessType.Default, ClientType.ATM, "GRWSBS1", "20000");
	}

	@ApiOperation(value = "POST校验短信验证码", tags = "手机验证码操作接口", notes = "该请求需要登录")
	@PostMapping("/sms/verify")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void validateCaptcha(@RequestBody ValidateCaptchaDTO dto) {
		String sms = captchaRequestService.smsCaptchaRequest(dto.getMobilenumber(), dto.getCaptcha());
		if (!"".equals(sms)) {
			throw new CaptchaErrorException(sms);
		}
	}

	/**
	 * 根据姓名、证件号 获取接受短信验证码的手机号
	 * 
	 * @param
	 */
	@ApiOperation(value = "POST根据账号 获取接受短信验证码的手机号", tags = "手机验证码操作接口", notes = "根据账号 获取接受短信验证码的手机号")
	@RequestMapping(value = "/captcha/sm/idnumbername", method = RequestMethod.POST)
	public MobileQueryDTO getMobileNumberByAccountToUser(@RequestBody MobileQueryDTO dto) {
		log.debug("根据账号 获取接受短信验证码的手机号 姓名={}，证件号={}", dto.getName(), dto.getIdNumber());
		if (dto.getName() == null || "".equals(dto.getName())) {
			throw new ResourceNotFoundException("请输入您的姓名");
		}
		if (dto.getIdNumber() == null || "".equals(dto.getIdNumber())) {
			throw new ResourceNotFoundException("请输入正确位数的证件号码");
		}
		ThinUser userInfo = thinUserRepository.findByIdNumber(dto.getIdNumber());
		if (userInfo == null || !"2".equals(userInfo.getUserTypeString())) {
			throw new ResourceNotFoundException("根据您输入的证件号码未查询到有效的账户，请重新输入");
		}
		if (!userInfo.getName().equals(dto.getName())) {
			throw new ResourceNotFoundException("您输入的姓名与账户信息不匹配，请重新输入");
		}
		String mobilenumber = userInfo.getMobile();
		if (mobilenumber == null || "".equals(mobilenumber)) {
			throw new ResourceNotFoundException("您的账户未预留手机号码");
		}
		String encodeNumber = mobilenumber.substring(0, 3) + "****" + mobilenumber.substring(7);
		MobileQueryDTO mobiledto = new MobileQueryDTO(encodeNumber);
		return mobiledto;
	}

	/**
	 * 根据姓名身份证号发送验证码
	 * 
	 * @param dto
	 */
	@ApiOperation(value = "POST根据姓名身份证号发送验证码", tags = "手机验证码操作接口", notes = "根据姓名身份证号发送验证码")
	@RequestMapping(value = "/captcha/sm/{clientType}/byidnumbername", method = RequestMethod.POST)
	public MobileQueryDTO sendCaptchaSmsByAccountToUser(@PathVariable("clientType") String clientType, @RequestBody MobileQueryDTO dto) {
		log.debug("根据姓名身份证号发送验证码 姓名：{} , 证件号：{}", dto.getName(), dto.getIdNumber());
		if (dto.getName() == null || "".equals(dto.getName())) {
			throw new ResourceNotFoundException("请输入您的姓名");
		}
		if (dto.getIdNumber() == null || "".equals(dto.getIdNumber())) {
			throw new ResourceNotFoundException("请输入正确位数的证件号码");
		}
		ThinUser userInfo = thinUserRepository.findByIdNumber(dto.getIdNumber());
		if (userInfo == null || !"2".equals(userInfo.getUserTypeString())) {
			throw new ResourceNotFoundException("根据您输入的证件号码未查询到有效的账户，请重新输入");
		}
		if (!userInfo.getName().equals(dto.getName())) {
			throw new ResourceNotFoundException("您输入的姓名与账户信息不匹配，请重新输入");
		}
		String mobilenumber = userInfo.getMobile();
		if (mobilenumber == null || "".equals(mobilenumber)) {
			throw new ResourceNotFoundException("您的账户未预留手机号码");
		}

		// String random = RandomStringUtils.randomAlphanumeric(24);
		if ("mobile".equals(clientType)) {// 手机端
			// 个人登录密码重置
			msgService.nextCaptchaPersonSms(mobilenumber, dto.getIdNumber(), BusinessType.PwdReset, ClientType.Mobile, "GRWSBS4", "20000");
		} else if ("web".equals(clientType)) {// pc，这个请求是会校验图形验证码的。
			// 个人登录密码重置
			msgService.nextCaptchaPersonSms(mobilenumber, dto.getIdNumber(), BusinessType.PwdReset, ClientType.PC, "GRWSBS4", "20000");
			// ResetPasswordForTokenDTO passwordForTokenDTO = new
			// ResetPasswordForTokenDTO(random, "", dto.getIdNumber(),
			// dto.getName());
			// resetPasswordTokenService.saveResetPassword(passwordForTokenDTO);
		} else {
			log.error("clientType={}有误", clientType);
			throw new MessageException("请求有误");
		}
		String encodeNumber = mobilenumber.substring(0, 3) + "****" + mobilenumber.substring(7);
		MobileQueryDTO mobiledto = new MobileQueryDTO(encodeNumber);
		// mobiledto.setRandom(random);
		return mobiledto;
	}

	@ApiOperation(value = "POST根据专家编号发送验证码", tags = "手机验证码操作接口", notes = "根据专家编号发送验证码")
	@RequestMapping(value = "/captcha/sm/byexpert", method = RequestMethod.POST)
	public MobileQueryDTO sendCaptchaSmsByExpertAccount(@RequestBody MobileQueryDTO dto, HttpServletRequest request) {
		log.debug("根据专家编号发送验证码 姓名={}, 证件号={}, 专家编号={}", dto.getName(), dto.getIdNumber(), dto.getPersonNumber());

		Validate.notBlank(dto.getName(), "请输入您的姓名");
		Validate.notBlank(dto.getIdNumber(), "请输入证件号码");
		Validate.notBlank(dto.getPersonNumber(), "请输入专家编号");

		String img = captchaRequestService.imgCaptchaRequest(request);
		if (!"".equals(img)) {
			throw new ImageCaptchaException(img);
		}

		PersonExpert expert = personExpertRepository.findById(dto.getPersonNumber());
		if (expert == null) {
			throw new BusinessException("根据您输入的专家编号 " + dto.getPersonNumber() + " 未查询到关联的专家信息，请仔细核对");
		}

		if (!(dto.getIdNumber().equals(expert.getIdNumber()))) {
			throw new BusinessException("您输入的证件号码与登记的证件号码不一致，请仔细核对");
		}

		if (!(dto.getName().equals(expert.getName()))) {
			throw new BusinessException("您输入的姓名与登记的姓名不一致，请仔细核对");
		}

		String mobilenumber = expert.getMobile();
		Validate.notBlank(mobilenumber, "您的专家信息未预留手机号码");

		msgService.nextCaptchaSms(mobilenumber, dto.getIdNumber(), SystemType.Default, UserType.PERSON, BusinessType.Default, ClientType.PC, "GRWSBS4", "20000");

		String encodeNumber = mobilenumber.substring(0, 3) + "****" + mobilenumber.substring(7);
		MobileQueryDTO mobiledto = new MobileQueryDTO(encodeNumber);
		return mobiledto;
	}

}
