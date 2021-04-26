package com.neusoft.ehrss.liaoning.security.implicit.weixin.userdetailsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.security.implicit.wechat.userdetails.WechatUserDetails;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;

/**
 * 微信
 * 
 * @author zhou.haidong
 *
 */
@Service
public class WeixinUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(WeixinUserDetailsService.class);

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================WechatUserDetailsService获取用户信息username={}========", username);
		ThinUser user = thinUserRepository.findByIdNumber(username);
		if (null == user || !"2".equals(user.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}
		if (!user.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		if (user.isRealNameAuthed() || user.isBindCardAuthed()) {
			// 更新角色和人员信息
			userCustomService.updateUserForPersonAndRole(user.getIdNumber());
		}
		return new WechatUserDetails(user);
	}

}