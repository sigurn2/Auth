package com.neusoft.sl.si.authserver.uaa.userdetails.siagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;

/**
 * 扩展UserDetailService基层受理用户的实现
 */
@Service
public class SiAgentUserDetailService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(SiAgentUserDetailService.class);

	@Autowired
	private ThinUserRepository thinUserRepository;

	/**
	 * 载入用户
	 */

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================获取用户信息username={}========================", username);
		ThinUser user = thinUserRepository.findByAccount(username);
		if (null == user || !"6".equals(user.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}
		if (!user.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		return new SiAgentUserDetails(user);
	}
}
