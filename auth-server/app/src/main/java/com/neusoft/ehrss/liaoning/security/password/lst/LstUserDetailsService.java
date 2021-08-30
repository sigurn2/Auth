package com.neusoft.ehrss.liaoning.security.password.lst;

import javax.annotation.Resource;

import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTOAssembler;
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

import java.util.UUID;

@Service
public class LstUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(LstUserDetailsService.class);

	@Resource
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

    // 本溪辽事通对接
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================MobileUserDetailsService获取用户信息username={}========", username);
		ThinUser thinUser = thinUserRepository.findByIdNumber(username);
		if (null == thinUser || !"2".equals(thinUser.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}
		if (!thinUser.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		return new LstUserDetails(thinUser);
	}



}