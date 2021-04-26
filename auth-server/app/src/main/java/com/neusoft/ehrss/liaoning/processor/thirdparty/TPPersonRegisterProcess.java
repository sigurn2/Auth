package com.neusoft.ehrss.liaoning.processor.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPResultDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPPersonRegisterRequest;
import com.neusoft.ehrss.liaoning.processor.thirdparty.response.TPResultReponse;
import com.neusoft.sl.girder.utils.UuidUtils;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 第三方用户注册
 * 
 * @author mujf
 *
 */
@Service(value = "tppersonRegisterProcess")
public class TPPersonRegisterProcess extends AbstractBusinessProcessor<TPPersonRegisterRequest, TPResultReponse> implements BusinessProcessor<TPPersonRegisterRequest, TPResultReponse> {
	@Autowired
	private UserCustomService userCustomService;

	@Override
	public boolean isTransaction() {
		return true;
	}

	@Override
	protected TPResultReponse processBusiness(TPPersonRegisterRequest req) {
		TPResultDTO rsdto = new TPResultDTO();
		try {
			PersonUserDTO userDTO = req.getPersonUserDTO();
			LOGGER.debug("第三方个人用户登记DTO={}", userDTO);

			userDTO.setIdType("01");// 不应该写死

			// 如果account是空，就生成一个UUID
			if (StringUtils.isEmpty(userDTO.getAccount())) {
				userDTO.setAccount(UuidUtils.getRandomUUIDString());
			}

			User user = PersonUserDTOAssembler.crtfromDTO(userDTO);
			user.setExtension("third_register");

			// 保存user对象
			user = userCustomService.createPerson(user);
			// 保存注册日志
			UserLogManager.saveRegisterLog(SystemType.Person.toString(), "RLZYSC", user, null);
			rsdto.setName(user.getName());
			rsdto.setIdNumber(user.getIdNumber());
			if (StringUtils.isEmpty(user.getMobile())) {
				rsdto.setMobilenumber("");
			} else {
				rsdto.setMobilenumber(user.getMobile());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new TPResultReponse(req, e.getMessage());
		}
		return new TPResultReponse(req, rsdto);
	}

}
