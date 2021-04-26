package com.neusoft.ehrss.liaoning.processor.thirdparty;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPEnterpriseUserDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPResultDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPCompanyRegisterRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.TPResultReponse;
import com.neusoft.sl.si.authserver.base.domains.user.EnterpriseUserFactory;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 第三方企业用户注册
 * 
 * @author zhou.haidong
 *
 */
@Service(value = "tpcompanyRegisterProcess")
public class TPCompanyRegisterProcess extends AbstractBusinessProcessor<TPCompanyRegisterRequest, TPResultReponse> implements BusinessProcessor<TPCompanyRegisterRequest, TPResultReponse> {

	@Autowired
	private UserCustomService userCustomService;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Override
	public boolean isTransaction() {
		return true;
	}

	@Override
	protected TPResultReponse processBusiness(TPCompanyRegisterRequest req) {
		TPEnterpriseUserDTO userDTO = req.getTPEnterpriseUserDTO();
		LOGGER.debug("第三方企业用户登记DTO={}", userDTO);
		TPResultDTO rsdto = new TPResultDTO();
		try {
			Validate.notBlank(userDTO.getAccount(), "请输入用户名");
			Validate.notBlank(userDTO.getOrgCode(), "请输入统一社会信用代码");
			Validate.notBlank(userDTO.getName(), "请输入单位名称");
			Validate.notBlank(userDTO.getPassword(), "请输入密码");

			// 根据统一信用代码查询是否存在账号
			ThinUser thinUser = thinUserRepository.findByOrgCode(userDTO.getOrgCode());
			if (thinUser != null) {
				throw new BusinessException(userDTO.getAccount() + "已注册，用户名为" + userDTO.getAccount() + "，请直接登录");
			}
			thinUser = thinUserRepository.findByAccount(userDTO.getAccount());
			if (thinUser != null) {
				throw new BusinessException("用户名" + userDTO.getAccount() + "已被使用，请修改");
			}

			// 一切顺利，创建user
			EnterpriseUserFactory factory = new EnterpriseUserFactory();
			User user = factory.createEnterpriseUser("0", userDTO.getAccount(), userDTO.getName(), "", "", null);
			user.setOrgCode(userDTO.getOrgCode());
			user.activeUser();
			user.setPassword(userDTO.getPassword());
			user.setExtension("third_register");

			// 保存user对象
			user = userCustomService.createEnterpriseUserWithOrgCode(user);
			// 保存注册日志
			UserLogManager.saveRegisterLog(SystemType.Enterprise.toString(), "RLZYSC", user, null);
			rsdto.setName(user.getName());
			rsdto.setOrgCode(user.getOrgCode());
			rsdto.setAccount(user.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
			return new TPResultReponse(req, e.getMessage());
		}
		return new TPResultReponse(req, rsdto);
	}

}
