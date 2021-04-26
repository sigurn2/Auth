package com.neusoft.ehrss.liaoning.security.password.refresh_token;

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

@Service
public class RefreshTokenDetailsService implements UserDetailsService {

	@Autowired
	private ThinUserRepository thinUserRepository;

	private static final Logger log = LoggerFactory.getLogger(RefreshTokenDetailsService.class);

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================RefreshTokenDetailsService获取用户信息username={}========================", username);
		ThinUser user = thinUserRepository.findByAccount(username);
		if (null == user) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		} else if (!user.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		return new RefreshTokenDetails(user);
	}

}