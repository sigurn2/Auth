package com.neusoft.ehrss.liaoning.security.implicit.wechat.userdetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.security.implicit.wechat.WxUserDTO;
import com.neusoft.sl.si.authserver.base.domains.openid.Openid;
import com.neusoft.sl.si.authserver.base.domains.openid.OpenidRepository;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.redis.WxUserRedisService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.uaa.exception.WechatNotBindException;

/**
 * 微信
 * 
 * @author zhou.haidong
 *
 */
@Service
public class WechatUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(WechatUserDetailsService.class);

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	@Autowired
	private OpenidRepository openidRepository;

	@Autowired
	private WxUserRedisService wxUserRedisService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("================WechatUserDetailsService获取用户信息username={}========", username);
		Openid open = openidRepository.findByOpenid(username);

		if (null == open) {
			throw new WechatNotBindException("该公众号未绑定用户", username);
		} else {
			WxUserDTO userDTO = wxUserRedisService.load(username);
			if (!(userDTO.getAppId().equals(open.getWechatAccountType()))) {
				throw new WechatNotBindException("该公众号未绑定用户", username);
			}
		}
		ThinUser user = thinUserRepository.findByIdNumber(open.getIdNumber());
		if (null == user || !"2".equals(user.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}
		if (!user.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		// 更新角色和人员信息
		userCustomService.updateUserForPersonAndRole(user.getIdNumber());
		WechatUserDetails detail = new WechatUserDetails(user);
		detail.setOpenid(username);
		return detail;
	}

}