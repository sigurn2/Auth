package com.neusoft.ehrss.liaoning.family.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.family.dto.FamilyAssembler;
import com.neusoft.ehrss.liaoning.family.dto.FamilyDTO;
import com.neusoft.ehrss.liaoning.family.dto.FamilyQueryDTO;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.family.Family;
import com.neusoft.sl.si.authserver.base.domains.family.FamilyRepository;
import com.neusoft.sl.si.authserver.base.domains.family.ThinUserFamily;
import com.neusoft.sl.si.authserver.base.domains.family.ThinUserFamilyRepository;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;
import com.neusoft.sl.si.authserver.base.domains.person.authed.PersonAuthedLogBack;
import com.neusoft.sl.si.authserver.base.domains.person.authed.PersonAuthedLogBackRepository;
import com.neusoft.sl.si.authserver.base.domains.role.InitRoleProvider;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.UserRepository;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;

@Service
public class FamilyService {

	@Autowired
	private FamilyRepository familyRepository;

	@Resource
	private UserRepository userRepository;

	@Autowired
	private ThinUserFamilyRepository thinUserFamilyRepository;

	@Resource
	private RedisAccountService redisAccountService;

	@Resource(name = "personUserRoleProvider")
	private InitRoleProvider personUserRoleProvider;

	@Resource
	private PersonRepositoryExtend personRepositoryExtend;

	@Autowired
	private PersonAuthedLogBackRepository authedLogBackRepository;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createFamily(String account, List<FamilyDTO> dtos, String authClient, String authType) {
		// 校验
		if (dtos == null || dtos.size() == 0) {
			throw new RuntimeException("家庭成员集合为空");
		}

		// 先校验是否存在账户
		User thinUserFamily = userRepository.findByAccount(account);
		if (thinUserFamily == null || !"2".equals(thinUserFamily.getUserTypeString())) {
			throw new ResourceNotFoundException("未找到有效用户");
		}
		for (FamilyDTO dto : dtos) {
			Validate.notBlank(dto.getFamilyId(), "家庭成员ID为空");
			Validate.notBlank(dto.getName(), "真实姓名为空");
			Validate.notBlank(dto.getSex(), "性别为空");
			Validate.notBlank(dto.getBirthday(), "出生日期为空");
			Validate.notBlank(dto.getIdNumber(), "身份证号为空");
			Validate.notBlank(dto.getMobile(), "联系电话为空");
			Validate.notNull(dto.isDef(), "默认就诊人为空");
			Validate.notNull(dto.isAuth(), "是否实名认证为空");
			// 在校验是否存在家庭成员
			Family family = familyRepository.findByFamilyId(dto.getFamilyId());
			boolean isAuth = false;
			// 存在家庭成员，只更新字段
			if (family != null) {
				family.setFamilyId(dto.getFamilyId());
				family.setRelation(dto.getRelation());
				family.setName(dto.getName());
				family.setSex(dto.getSex());
				family.setBirthday(dto.getBirthday());
				family.setIdNumber(dto.getIdNumber());
				if (!StringUtils.isEmpty(dto.getMobile()))
					family.setMobile(dto.getMobile());
				family.setPersonNumber(dto.getPersonNumber());
				family.setSocialEnsureNumber(dto.getSocialEnsureNumber());
				if (!StringUtils.isEmpty(dto.getCardNumber()))
					family.setCardNumber(dto.getCardNumber());
				family.setCityId(dto.getCityId());
				family.setSiTypeCode(dto.getSiTypeCode());
				family.setChronic(dto.isChronic());
				family.setDef(dto.isDef());
				isAuth = family.isAuth();
				family.setAuth(dto.isAuth());
				// throw new RuntimeException("家庭成员已经存在");
			} else {
				// 不存在时新增
				family = FamilyAssembler.toFamily(dto);
			}
			// 状态是实名的
			if (dto.isAuth()) {
				// 如果是主账号并且实名，主账号实名后不会被注销实名，家庭成员是有可能的
				if (thinUserFamily.getIdNumber().equals(dto.getIdNumber())) {
					if (!thinUserFamily.isRealNameAuthed()) {
						String date = LongDateUtils.nowAsSecondLong().toString();
						thinUserFamily.setRealNameAuthed(true);// 主账号实名
						thinUserFamily.setRealNameDate(date);
						// 修改手机号，去掉手机号修改的逻辑，edit 2019/2/20
//						if (!StringUtils.isEmpty(family.getMobile())) {
//							if (StringUtils.isEmpty(thinUserFamily.getMobile()) || !thinUserFamily.getMobile().equals(family.getMobile())) {
//								thinUserFamily.setMobile(family.getMobile());// 修改手机号
//							}
//						}
						// 保存实名认证日志
						savePersonAuth(authClient, date, thinUserFamily.getAccount(), thinUserFamily.getIdNumber(), family.getName(), thinUserFamily.getMobile(), "user" + authType, family.getCardNumber());
					}
					if (!thinUserFamily.getName().equals(dto.getName())) {
						thinUserFamily.setName(dto.getName());// 修改名字
					}
				} else {
					if (dto.isAuth() && !isAuth) {
						// 记录实名
						String date = LongDateUtils.nowAsSecondLong().toString();
						// 保存实名认证日志
						savePersonAuth(authClient, date, account, family.getIdNumber(), family.getName(), family.getMobile(), "family" + authType, family.getCardNumber());
					}
				}
			}
			// 保存家庭成员信息
			familyRepository.save(family);
			// 保存关联信息
			thinUserFamily.addFamily(family);
		}
		// 最后保存
		userRepository.save(thinUserFamily);
		redisAccountService.modifyRedis(account);
	}

	private void savePersonAuth(String authClient, String authDate, String personAccount, String personIdNumber, String personName, String personMobile, String authType, String personCardNumber) {
		PersonAuthedLogBack authedLogBack = new PersonAuthedLogBack();
		authedLogBack.setAuthClient(authClient);
		authedLogBack.setAuthDate(authDate);// 实名时间
		authedLogBack.setPersonAccount(personAccount);
		authedLogBack.setPersonIdNumber(personIdNumber);
		authedLogBack.setPersonName(personName);
		authedLogBack.setPersonMobile(personMobile);
		authedLogBack.setAuthType(authType);
		authedLogBack.setPersonCardNumber(personCardNumber);
		authedLogBackRepository.save(authedLogBack);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteFamily(String account, String familyId) {
		// 先校验是否存在账户
		User thinUserFamily = userRepository.findByAccount(account);
		if (thinUserFamily == null || !"2".equals(thinUserFamily.getUserTypeString())) {
			throw new ResourceNotFoundException("未找到有效用户");
		}
		Set<Family> families = thinUserFamily.getFamilies();
		if (families != null && families.size() > 0) {
			for (Family family : families) {
				if (family.getFamilyId().equals(familyId)) {
					thinUserFamily.removeFamily(family);
					// thinUserFamilyRepository.save(thinUserFamily);
					familyRepository.delete(family);
					userRepository.save(thinUserFamily);
					redisAccountService.modifyRedis(account);
					break;
				}
			}
		}
	}

	public List<FamilyQueryDTO> queryFamily(String account) {
		ThinUserFamily thinUserFamily = thinUserFamilyRepository.findByAccount(account);
		if (thinUserFamily == null || !"2".equals(thinUserFamily.getUserTypeString())) {
			throw new ResourceNotFoundException("未找到有效用户");
		}
		Set<Family> families = thinUserFamily.getFamilies();
		return FamilyAssembler.toAssociatedFamilies(families);
	}

}
