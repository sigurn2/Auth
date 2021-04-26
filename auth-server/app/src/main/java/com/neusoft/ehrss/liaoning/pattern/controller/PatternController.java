package com.neusoft.ehrss.liaoning.pattern.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.pattern.controller.dto.UserPatternDTO;
import com.neusoft.ehrss.liaoning.pattern.service.PatternService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pattern")
public class PatternController {

	private static final Logger log = LoggerFactory.getLogger(PatternController.class);

	@Autowired
	private PatternService patternService;

	@ApiOperation(value = "POST设置手势密码", tags = "手势密码操作接口", notes = "设置手势密码PatternController")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void bind(Principal user, @RequestBody UserPatternDTO dto) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		String account = (String) principal;
		log.debug("绑定手势密码，account={}", account);
		if (StringUtils.isBlank(dto.getAppType())) {
			dto.setAppType("1");
		}
		this.patternService.bind(account, dto);
	}

	@ApiOperation(value = "POST校验手势密码", tags = "手势密码操作接口", notes = "校验手势密码PatternController")
	@PostMapping("/verify")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void verify(Principal user, @RequestBody UserPatternDTO dto) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuth = authentication.getUserAuthentication();
		Object principal = userAuth.getPrincipal();
		String account = (String) principal;
		log.debug("校验手势密码，account={}", account);
		if (StringUtils.isBlank(dto.getAppType())) {
			dto.setAppType("1");
		}
		this.patternService.verify(account, dto);
	}

}
