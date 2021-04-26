package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.ExpertUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.ClientType;

import io.swagger.annotations.ApiOperation;

/**
 * 用户接口控制器
 * 
 * @author mojf
 * 
 */
@RestController
@RequestMapping("/ws/user")
public class UserInterfaceRestController {

	private static final Logger log = LoggerFactory.getLogger(UserInterfaceRestController.class);

	@Autowired
	private UserCustomService userCustomService;

	/**
	 * 激活用户
	 * 
	 * @param account
	 */
	@ApiOperation(value = "PUT激活专家用户", tags = "用户操作接口", notes = "激活专家用户UserInterfaceRestController")
	@RequestMapping(value = "/expert/active", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void expertUser(@RequestBody PersonUserDTO userDTO, HttpServletRequest request) {
		log.debug("激活专家用户，专家编号={}", userDTO.getPersonNumber());
		Validate.notBlank(userDTO.getIdNumber(), "请输入证件号码");
		Validate.notBlank(userDTO.getPersonNumber(), "请输入专家编号");
		// 如果account是空，就生成一个UUID
		if (StringUtils.isEmpty(userDTO.getAccount())) {
			userDTO.setAccount(UuidUtils.getRandomUUIDString());
		}
		User user = ExpertUserDTOAssembler.crtfromDTO(userDTO);
		user.setExtension("pc_active");
		// 激活网报账户
		user = userCustomService.createExpertUser(user, userDTO.getPersonNumber(), request);
		UserLogManager.saveRegisterLog("Expert", ClientType.PC.toString(), user, request);
	}

	/**
	 * 根据身份证号或手机号码查询用户
	 * 
	 * @param idormobile
	 * @return
	 */
	/*
	 * @ApiOperation(value = "GET根据身份证号或手机号码查询用户", tags = "用户操作接口", notes =
	 * "根据身份证号或手机号码查询用户UserInterfaceRestController", response =
	 * AuthenticatedCasUser.class)
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/byidormobile/{idormobile}", method =
	 * RequestMethod.GET) public AuthenticatedCasUser
	 * queryPersonUser(@PathVariable("idormobile") String idOrMobile) { User
	 * userInfo = userService.findByIdNumber(idOrMobile); if (null == userInfo)
	 * { userInfo = userService.findByMobileNumber(idOrMobile); if (null ==
	 * userInfo) { throw new ResourceNotFoundException("根据" + idOrMobile +
	 * "未找到有效的用户"); } } return UserAssembler.toDTO(userInfo); }
	 */

}
