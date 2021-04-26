package com.neusoft.ehrss.liaoning.pattern.service;

import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.ehrss.liaoning.pattern.controller.dto.UserPatternDTO;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserPattern;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserPatternRepository;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPattern;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPatternRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Service
public class PatternServiceImpl implements PatternService {

	@Autowired
	private UserPatternRepository userPatternRepository;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Autowired
	private ThinUserPatternRepository thinUserPatternRepository;

	@Resource
	private RedisAccountService redisAccountService;

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void bind(String account, UserPatternDTO dto) {
		Validate.notBlank(dto.getDeviceId(), "设备唯一标识为空");
		Validate.notBlank(dto.getPatternPwd(), "手势密码为空");
		Validate.notBlank(dto.getAppType(), "设置的类型为空");

		// 采用跟常州不同的方案，任性。数据库只会保留一个人的最后设置的设备信息
		// 查询用户
		ThinUserPattern thinUser = thinUserPatternRepository.findByAccount(account);
		// 判断用户是否存在
		if (thinUser == null || !"2".equals(thinUser.getUserTypeString())) {
			throw new ResourceNotFoundException("未找到有效的用户");
		}

		Set<UserPattern> patterns = thinUser.getPatterns();
		for (UserPattern userPattern : patterns) {
			if (userPattern.getAppType().equals(dto.getAppType())) {
				thinUser.removePattern(userPattern);
				break;
			}
		}
		UserPattern pattern = new UserPattern();
		pattern.setAppType(dto.getAppType());
		pattern.setDeviceId(dto.getDeviceId());
		pattern.setPatternPwd(passwordEncoderService.encryptPassword(dto.getPatternPwd()));
		pattern.setUserId(thinUser.getId());
		pattern.setAvailable(true);// 是否有效
		thinUser.addPattern(pattern);
		thinUserPatternRepository.save(thinUser);
		// 目前平行权限还用不到手势，而且清空平行权限会有问题，当前是登录状态
		redisAccountService.modifyOnlyRedisAccount(account);
	}

	public void verify(String account, UserPatternDTO dto) {
		Validate.notBlank(dto.getDeviceId(), "设备唯一标识为空");
		Validate.notBlank(dto.getPatternPwd(), "手势密码为空");
		Validate.notBlank(dto.getAppType(), "设置的类型为空");

		// 查询用户
		ThinUser thinUser = thinUserRepository.findByAccount(account);
		// 判断用户是否存在
		if (thinUser == null || !"2".equals(thinUser.getUserTypeString())) {
			throw new ResourceNotFoundException("未找到有效的用户");
		}
		// 查询是否有已经关联的手势密码
		Specification<UserPattern> specification = new Specification<UserPattern>() {
			@Override
			public Predicate toPredicate(Root<UserPattern> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("userId"), thinUser.getId());// 用户id
				Predicate p2 = cb.equal(root.get("available"), true);// 有效
				Predicate p3 = cb.equal(root.get("appType"), dto.getAppType());// 类型
				Predicate p4 = cb.equal(root.get("deviceId"), dto.getDeviceId());// 设备唯一标识
				return query.where(p1, p2, p3, p4).getRestriction();
			}
		};
		// 查询
		UserPattern userPattern = userPatternRepository.findOne(specification);
		// 判断是否为null
		if (userPattern == null) {
			throw new ResourceNotFoundException("未设置手势密码");
		}
		if (!passwordEncoderService.matches(dto.getPatternPwd(), userPattern.getPatternPwd())) {
			throw new BusinessException("手势错误");
		}
	}

}
