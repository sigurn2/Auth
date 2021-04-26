package com.neusoft.ehrss.liaoning.processor.thirdparty;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPChangePasswordUserDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPChangePasswordRequest;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.yardman.server.message.DefaultResponse;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 第三方密码修改
 * 
 * @author mujf
 *
 */
@Service(value = "tpchangePasswordProcess")
public class TPChangePasswordProcess extends AbstractBusinessProcessor<TPChangePasswordRequest, DefaultResponse> implements BusinessProcessor<TPChangePasswordRequest, DefaultResponse> {

	// @Resource
	// private CompanyRepository companyRepository;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;

	@Override
	public boolean isTransaction() {
		return true;
	}

	@Override
	protected DefaultResponse processBusiness(TPChangePasswordRequest req) {
		TPChangePasswordUserDTO userDTO = req.getTpChangePasswordUserDTO();
		LOGGER.debug("第三方用户修改密码DTO", userDTO);
		try {
			Validate.notBlank(userDTO.getCtype(), "请输入操作指令");
			Validate.notBlank(userDTO.getAccount(), "请输入账号");
			Validate.notBlank(userDTO.getOldpassword(), "请输入旧密码");
			Validate.notBlank(userDTO.getNewpassword(), "请输入新密码");

			ThinUser user = null;
			// 企业用户修改密码统一信用代码
			if (userDTO.getCtype().equals("1")) {
				// 根据统一信用代码查询是否存在账号
				user = thinUserRepository.findByOrgCode(userDTO.getAccount());
				if (user == null) {
					user = thinUserRepository.findByAccount(userDTO.getAccount());
				}
			} else if (userDTO.getCtype().equals("2")) {// 个人用户修改密码
				user = thinUserRepository.findByIdNumber(userDTO.getAccount());
			}

			if (user == null) {
				throw new ResourceNotFoundException("根据：" + userDTO.getAccount() + " 未查询到账号信息");
			}

			// 校验旧密码
			if (passwordEncoderService.matches(userDTO.getOldpassword(), user.getPassword())) {
				LOGGER.debug("==matches " + userDTO.getOldpassword() + "======" + user.getPassword() + "=======");
				// 修改密码
				user.setPassword(passwordEncoderService.encryptPassword(userDTO.getNewpassword()));
				LOGGER.debug("==updateUser " + user + "=============");
				thinUserRepository.save(user);
			} else {
				throw new BadCredentialsException("您输入的旧密码有误，请重新输入");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultResponse(req, e.getMessage());
		}
		DefaultResponse result = new DefaultResponse(req);
		return result;
	}

}
