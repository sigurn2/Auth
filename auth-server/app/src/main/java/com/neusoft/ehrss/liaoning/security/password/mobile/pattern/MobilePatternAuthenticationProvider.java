package com.neusoft.ehrss.liaoning.security.password.mobile.pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.security.password.mobile.MobileUserDetails;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPattern;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPatternRepository;

/**
 * 带验证码的 用户名密码登录验证器
 * 
 * @author mojf
 *
 */

public class MobilePatternAuthenticationProvider extends DaoAuthenticationProvider {

	private UserPatternRepository userPatternRepository;

	public UserPatternRepository getUserPatternRepository() {
		return userPatternRepository;
	}

	public void setUserPatternRepository(UserPatternRepository userPatternRepository) {
		this.userPatternRepository = userPatternRepository;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (MobilePatternAuthenticationToken.class.isAssignableFrom(authentication)) && (MobilePatternAuthenticationToken.class.getSimpleName().equals(authentication.getSimpleName()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		Object salt = null;
		if (super.getSaltSource() != null) {
			salt = super.getSaltSource().getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException("账号或密码有误");
		}
		String presentedPassword = authentication.getCredentials().toString();// 输入的手势

		MobilePatternAuthenticationToken patternAuthentication = (MobilePatternAuthenticationToken) authentication;

		if (StringUtils.isEmpty(patternAuthentication.getDeviceId()))
			throw new BadCredentialsException("设备唯一标识为空");

		String appType = patternAuthentication.getAppType();
		if (StringUtils.isEmpty(appType)) {
			appType = "1";
		}
		final String aT = appType;

		MobileUserDetails mobileUserDetails = (MobileUserDetails) userDetails;

		// 查询是否有已经关联的手势密码
		Specification<UserPattern> specification = new Specification<UserPattern>() {
			@Override
			public Predicate toPredicate(Root<UserPattern> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("userId"), mobileUserDetails.getId());// 用户id
				Predicate p2 = cb.equal(root.get("available"), true);// 有效
				Predicate p3 = cb.equal(root.get("appType"), aT);// 类型-1人社
				Predicate p4 = cb.equal(root.get("deviceId"), patternAuthentication.getDeviceId());// 设备唯一标识
				return query.where(p1, p2, p3, p4).getRestriction();
			}
		};

		UserPattern userPattern = userPatternRepository.findOne(specification);
		if (userPattern == null) {
			throw new BadCredentialsException("未开启登录手势密码");
		}

		if (!getPasswordEncoder().isPasswordValid(userPattern.getPatternPwd(), presentedPassword, salt)) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException("手势密码错误");
		}
	}

}
