package com.neusoft.ehrss.liaoning.processor.thirdparty;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPResetSensitiveInfoDTO;
import com.neusoft.ehrss.liaoning.processor.thirdparty.request.TPResetSensitiveInfoRequest;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.validate.password.ValidatePassword;
import com.neusoft.sl.si.yardman.server.message.DefaultResponse;
import com.neusoft.sl.si.yardman.server.processor.AbstractBusinessProcessor;
import com.neusoft.sl.si.yardman.server.processor.BusinessProcessor;

/**
 * 第三方密码或手机号重置
 * 
 * @author mujf
 *
 */
@Service(value = "tpresetSensitiveInfoProcess")
public class TPResetSensitiveInfoProcess extends AbstractBusinessProcessor<TPResetSensitiveInfoRequest, DefaultResponse> implements BusinessProcessor<TPResetSensitiveInfoRequest, DefaultResponse> {

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;

	@Resource
	private RedisAccountService redisAccountService;

	@Override
	public boolean isTransaction() {
		return true;
	}

	@Override
	protected DefaultResponse processBusiness(TPResetSensitiveInfoRequest req) {
		TPResetSensitiveInfoDTO userDTO = req.getTpResetSensitiveInfoDTO();
		LOGGER.debug("第三方用户重置敏感信息DTO", userDTO);
		try {
			Validate.notBlank(userDTO.getCtype(), "请输入操作指令");
			Validate.notBlank(userDTO.getAccount(), "请输入账号");
			if (StringUtils.isBlank(userDTO.getPassword()) && StringUtils.isBlank(userDTO.getMobilenumber())) {
				throw new BusinessException("密码与手机号码至少输入一项");
			}

			ThinUser user = null;
			// 企业用户修改密码统一信用代码-只重置密码即可
			if (userDTO.getCtype().equals("1")) {
				// 根据统一信用代码查询是否存在账号
				user = thinUserRepository.findByOrgCode(userDTO.getAccount());
				if (user == null) {
					user = thinUserRepository.findByAccount(userDTO.getAccount());
				}
				userDTO.setMobilenumber(null);
			} else if (userDTO.getCtype().equals("2")) {// 个人用户修改密码
				user = thinUserRepository.findByIdNumber(userDTO.getAccount());
			}

			if (user == null) {
				throw new ResourceNotFoundException("根据：" + userDTO.getAccount() + " 未查询到账号信息");
			}
			// 修改密码
			if (StringUtils.isNotBlank(userDTO.getPassword())) {
				ValidatePassword.verifyPassword(userDTO.getPassword());
				user.setPassword(passwordEncoderService.encryptPassword(userDTO.getPassword()));
			}
			// 修改手机号
			if (StringUtils.isNotBlank(userDTO.getMobilenumber())) {
				// 已经绑定了手机号 相同就不需要更新了
				if (!(StringUtils.isNotBlank(user.getMobile()) && user.getMobile().equals(userDTO.getMobilenumber()))) {
					ThinUser mobile = thinUserRepository.findByMobile(userDTO.getMobilenumber());
					if (mobile != null) {
						mobile.setMobile(null);
						thinUserRepository.save(mobile);
						redisAccountService.modifyOnlyRedisAccount(mobile.getAccount());
					}
				}
				user.setMobile(userDTO.getMobilenumber());
			}
			thinUserRepository.save(user);
			redisAccountService.modifyOnlyRedisAccount(user.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultResponse(req, e.getMessage());
		}
		DefaultResponse result = new DefaultResponse(req);
		return result;
	}

}
