package com.neusoft.sl.si.authserver.uaa.userdetails.person;

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

/**
 * 扩展UserDetailService个人用户的实现
 */
@Service
public class PersonUserDetailService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(PersonUserDetailService.class);

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private UserCustomService userCustomService;

	/**
	 * 载入用户
	 */

	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		log.debug("================通过身份证号获取个人用户信息account={}========================", account);
		ThinUser user = thinUserRepository.findByIdNumber(account);
		if (null == user || !"2".equals(user.getUserTypeString())) {
			throw new BadCredentialsException("您输入的账号不存在，请重新输入");
		}
		if (!user.isActivated()) {
			throw new BadCredentialsException("您输入的账号未激活");
		}
		// if (!user.isRealNameAuthed()) {
		// throw new UsernameNotFoundException("用户未实名认证");
		// }
//		if (user.isRealNameAuthed() || user.isBindCardAuthed()) {
//			// 更新角色和人员信息
//			userCustomService.updateUserForPersonAndRole(user.getIdNumber());
//		}
		userCustomService.updateUserForPersonAndRoleByZwfw(user.getIdNumber(),user.getName());
		return new PersonIdOrMobileUserDetails(user);
	}
}
