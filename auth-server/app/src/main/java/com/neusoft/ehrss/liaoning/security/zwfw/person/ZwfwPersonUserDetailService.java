package com.neusoft.ehrss.liaoning.security.zwfw.person;

import com.neusoft.ehrss.liaoning.security.password.idnumbername.IdNumberNameUserDetails;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;
import com.neusoft.sl.si.authserver.uaa.userdetails.person.PersonIdOrMobileUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 扩展UserDetailService政务网的实现
 */
@Service
public class ZwfwPersonUserDetailService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(ZwfwPersonUserDetailService.class);

	// @Autowired
	// private UserService userService;

	@Autowired
	private ThinUserRepository thinUserRepository;


	@Autowired
	private UserCustomService userCustomService;

	/**
	 * 载入用户
	 */

	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		log.debug("================ZwfwPersonUserDetailService获取用户信息username={}========", account);
		String idNumber = account.split("@@")[0];
		String name = account.split("@@")[1];
		String mobile = account.split("@@")[2];
		String username = account.split("@@")[3];
		String uuid = account.split("@@")[4];
		log.debug("================ZwfwPersonUserDetailService获取用户信息={}， name = {}, mobile = {}, username = {},uuid={}========", idNumber, name, mobile, username,uuid);
		ThinUser thinUser = thinUserRepository.findByIdNumber(idNumber);
		if (null == thinUser || !"2".equals(thinUser.getUserTypeString())) {
			// thinUserPerson = thinUserRepository.findByIdNumber(username);
			// if (null == thinUserPerson ||
			// !"2".equals(thinUserPerson.getUserTypeString())) {

			try {
				User newUser = userCustomService.createPersonCloudbae(username, idNumber, name, mobile, "cloudbae_register", uuid);
				UserLogManager.saveRegisterLog(SystemType.Person.toString(), "Cloudbae", newUser, null);
			} catch (Exception e) {
				log.error("zwfw创建用户失败", e);
				throw new BadCredentialsException(e.getMessage());
			}

			thinUser = thinUserRepository.findByIdNumber(idNumber);
			return new IdNumberNameUserDetails(thinUser);
		} else {
			if (null == thinUser.getEmail()){
				userCustomService.updateEmailForZwfw(idNumber,uuid);
			}
			if (null == thinUser.getMobile()){
				log.debug("检测到数据库中无mobile，插入mobile={}",mobile);
				userCustomService.updateMobileForZwfw(idNumber,mobile);
			}
			if (!thinUser.isActivated()) {
				throw new BadCredentialsException("您输入的账号未激活");
			}

			return new IdNumberNameUserDetails(thinUser);
		}

	}


}
