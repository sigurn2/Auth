package com.neusoft.ehrss.liaoning.wechat.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.wechat.domain.dto.WechatUserDTO;
import com.neusoft.ehrss.liaoning.wechat.domain.service.WechatUserService;
import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

import io.swagger.annotations.ApiOperation;

/**
 * 微信接口，用户绑定，查询openid
 * 
 * @author zhou.haidong
 */
@RestController
@RequestMapping
public class WechatController {

	private static final Logger log = LoggerFactory.getLogger(WechatController.class);

	@Resource
	private CaptchaRequestService captchaRequestService;

	@Autowired
	private WechatUserService wechatUserService;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	@Resource
	private RedisAccountService redisAccountService;

	@ApiOperation(value = "POST微信校验用户是否已注册", tags = "微信操作接口", notes = "校验用户是否已注册WechatController")
	@GetMapping(value = "/ws/wechat/user/{idNumber}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void accountExists(HttpServletRequest request, @PathVariable String idNumber) {
		ThinUser thinUser = thinUserRepository.findByIdNumber(idNumber);
		if (null == thinUser) {
			throw new BusinessException("您输入的证件号码不存在，请重新输入");
		}
	}

	@ApiOperation(value = "POST微信绑定用户", tags = "微信操作接口", notes = "微信绑定用户WechatController")
	@PostMapping(value = "/ws/wechat/user")
	@ResponseStatus(HttpStatus.CREATED)
	public void bind(HttpServletRequest request, @RequestBody WechatUserDTO dto) {
		log.debug("微信账户绑定WechatUserBindDTO={}", dto);
		// 校验是否注册
		Validate.notBlank(dto.getOpenid(), "请求非法，OPENID为空");
		Validate.notBlank(dto.getName(), "请输入姓名");
		Validate.notBlank(dto.getIdNumber(), "请输入有效的证件号码");
		ThinUser thinUser = thinUserRepository.findByIdNumber(dto.getIdNumber());
		String mobile = request.getHeader("mobilenumber");
		if (thinUser == null) {
			Validate.notBlank(dto.getPassword(), "请输入密码");
			// 如果account是空，就生成一个UUID
			dto.setAccount(UuidUtils.getRandomUUIDString());
			User user = PersonUserDTOAssembler.crtfromDTO(dto);
			// 校验短信验证码
			String img = captchaRequestService.smsCaptchaRequest(request);
			if (!"".equals(img)) {
				throw new CaptchaErrorException(img);
			}
			user.setIdType("01");
			user.setMobile(mobile);
			user.setExtension("wechat_register");
			// 保存user对象
			user = userCustomService.createPerson(user);
			// 记录日志
			UserLogManager.saveRegisterLog(SystemType.Person.toString(), ClientType.WeChat.toString(), user, request);
		} else {
			Validate.notBlank(mobile, "请求中缺少必要的手机验证码信息");
			if (!StringUtils.isEmpty(thinUser.getMobile())) {
				if (!mobile.equals(thinUser.getMobile())) {
					throw new CaptchaErrorException("您输入的手机号码与预留的不匹配，请重新输入");
				}
			}
			// 校验短信验证码
			String img = captchaRequestService.smsCaptchaRequest(request);
			if (!"".equals(img)) {
				throw new CaptchaErrorException(img);
			}
			// 校验通过更新手机号
			if (StringUtils.isEmpty(thinUser.getMobile())) {
				// 手机号是否已注册
				ThinUser temp = thinUserRepository.findByMobile(mobile);
				// 取回手机号
				if (temp != null) {
					temp.setMobile(null);
					thinUserRepository.save(temp);
					redisAccountService.modifyOnlyRedisAccount(temp.getAccount());
				}
				thinUser.setMobile(mobile);
				thinUserRepository.save(thinUser);
				redisAccountService.modifyOnlyRedisAccount(thinUser.getAccount());
			}
		}
		// 绑定
		this.wechatUserService.bind(dto);
	}

	@ApiOperation(value = "PUT微信解绑", tags = "微信操作接口", notes = "校验用户是否已注册WechatController")
	@PostMapping(value = "/person/wechat/unbind/{type}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unbind(HttpServletRequest request, Principal user, @PathVariable String type) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		wechatUserService.unbind((String) principal, type);
	}

	// @ApiOperation(value = "GET根据微信公众号类型和身份证号查询微信openid", tags = "微信操作接口",
	// notes = "根据微信公众号类型和身份证号查询微信openid WechatController")
	// @GetMapping(value = "/ws/wechat/openid/{wechatAccountType}/{idNumber}")
	// public UserOpenidDTO getOpenid(@PathVariable("wechatAccountType") String
	// wechatAccountType, @PathVariable("idNumber") String idNumber) {
	// return wechatUserService.findOpenid(idNumber, wechatAccountType);
	// }

}
