package com.neusoft.ehrss.liaoning.security.password.mobile.person;

import javax.annotation.Resource;

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
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;

@Service
public class RlzyscPersonUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(RlzyscPersonUserDetailsService.class);

	@Resource
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================RlzyscPersonUserDetailsService获取用户信息username={}========", username);
		ThinUser thinUser = thinUserRepository.findByIdNumber(username);
		if (null == thinUser || !"2".equals(thinUser.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}

		if (!thinUser.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		if (thinUser.isRealNameAuthed() || thinUser.isBindCardAuthed()) {
			// 更新角色和人员信息
			userCustomService.updateUserForPersonAndRole(thinUser.getIdNumber());
		}
		return new RlzyscPersonUserDetails(thinUser);
	}

}