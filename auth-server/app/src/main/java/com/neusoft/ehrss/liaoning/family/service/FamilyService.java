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
		// ??????
		if (dtos == null || dtos.size() == 0) {
			throw new RuntimeException("????????????????????????");
		}

		// ???????????????????????????
		User thinUserFamily = userRepository.findByAccount(account);
		if (thinUserFamily == null || !"2".equals(thinUserFamily.getUserTypeString())) {
			throw new ResourceNotFoundException("?????????????????????");
		}
		for (FamilyDTO dto : dtos) {
			Validate.notBlank(dto.getFamilyId(), "????????????ID??????");
			Validate.notBlank(dto.getName(), "??????????????????");
			Validate.notBlank(dto.getSex(), "????????????");
			Validate.notBlank(dto.getBirthday(), "??????????????????");
			Validate.notBlank(dto.getIdNumber(), "??????????????????");
			Validate.notBlank(dto.getMobile(), "??????????????????");
			Validate.notNull(dto.isDef(), "?????????????????????");
			Validate.notNull(dto.isAuth(), "????????????????????????");
			// ?????????????????????????????????
			Family family = familyRepository.findByFamilyId(dto.getFamilyId());
			boolean isAuth = false;
			// ????????????????????????????????????
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
				// throw new RuntimeException("????????????????????????");
			} else {
				// ??????????????????
				family = FamilyAssembler.toFamily(dto);
			}
			// ??????????????????
			if (dto.isAuth()) {
				// ??????????????????????????????????????????????????????????????????????????????????????????????????????
				if (thinUserFamily.getIdNumber().equals(dto.getIdNumber())) {
					if (!thinUserFamily.isRealNameAuthed()) {
						String date = LongDateUtils.nowAsSecondLong().toString();
						thinUserFamily.setRealNameAuthed(true);// ???????????????
						thinUserFamily.setRealNameDate(date);
						// ???????????????????????????????????????????????????edit 2019/2/20
//						if (!StringUtils.isEmpty(family.getMobile())) {
//							if (StringUtils.isEmpty(thinUserFamily.getMobile()) || !thinUserFamily.getMobile().equals(family.getMobile())) {
//								thinUserFamily.setMobile(family.getMobile());// ???????????????
//							}
//						}
						// ????????????????????????
						savePersonAuth(authClient, date, thinUserFamily.getAccount(), thinUserFamily.getIdNumber(), family.getName(), thinUserFamily.getMobile(), "user" + authType, family.getCardNumber());
					}
					if (!thinUserFamily.getName().equals(dto.getName())) {
						thinUserFamily.setName(dto.getName());// ????????????
					}
				} else {
					if (dto.isAuth() && !isAuth) {
						// ????????????
						String date = LongDateUtils.nowAsSecondLong().toString();
						// ????????????????????????
						savePersonAuth(authClient, date, account, family.getIdNumber(), family.getName(), family.getMobile(), "family" + authType, family.getCardNumber());
					}
				}
			}
			// ????????????????????????
			familyRepository.save(family);
			// ??????????????????
			thinUserFamily.addFamily(family);
		}
		// ????????????
		userRepository.save(thinUserFamily);
		redisAccountService.modifyRedis(account);
	}

	private void savePersonAuth(String authClient, String authDate, String personAccount, String personIdNumber, String personName, String personMobile, String authType, String personCardNumber) {
		PersonAuthedLogBack authedLogBack = new PersonAuthedLogBack();
		authedLogBack.setAuthClient(authClient);
		authedLogBack.setAuthDate(authDate);// ????????????
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
		// ???????????????????????????
		User thinUserFamily = userRepository.findByAccount(account);
		if (thinUserFamily == null || !"2".equals(thinUserFamily.getUserTypeString())) {
			throw new ResourceNotFoundException("?????????????????????");
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
			throw new ResourceNotFoundException("?????????????????????");
		}
		Set<Family> families = thinUserFamily.getFamilies();
		return FamilyAssembler.toAssociatedFamilies(families);
	}

}
