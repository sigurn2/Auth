package com.neusoft.sl.si.authserver.uaa.userdetails.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.user.ThinUserWithRole;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserWithRoleRepository;

/**
 * 扩展UserDetailService企业用户的实现
 * 
 * 通过CA登录的
 * 
 */
@Service
public class EnterpriseCAUserDetailService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(EnterpriseCAUserDetailService.class);

	@Autowired
	private ThinUserWithRoleRepository thinUserWithRoleRepository;

	/**
	 * 载入用户
	 */

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("=======CA登录时=========获取企业用户信息username={}========================", username);
		ThinUserWithRole user = thinUserWithRoleRepository.findByAccount(username);
		if (null == user || !"1".equals(user.getUserTypeString())) {
			throw new UsernameNotFoundException("单位账号 [" + username + "] 未找到，请联系CA公司管理员");
		}
		if (!user.isActivated()) {
			throw new BadCredentialsException("单位账号 [" + username + "] 尚未激活，请联系CA公司管理员");
		}
		return new EnterpriseCAUserDetails(user);
	}
}
