package com.neusoft.ehrss.liaoning.family.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.neusoft.sl.girder.ddd.hibernate.utils.SpringContextUtils;
import com.neusoft.sl.si.authserver.base.domains.family.Family;
import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepositoryExtend;

public class FamilyAssembler {

	private static PersonRepositoryExtend personRepositoryExtend;

	private FamilyAssembler() {
		// hide for utils
	}

	private static PersonRepositoryExtend getPersonRepositoryExtend() {
		if (personRepositoryExtend == null) {
			personRepositoryExtend = SpringContextUtils.getBean("personRepositoryExtend");
		}
		return personRepositoryExtend;
	}

	public static Family toFamily(FamilyDTO dto) {
		if (dto == null) {
			return null;
		}
		Family family = new Family();
		family.setFamilyId(dto.getFamilyId());
		family.setRelation(dto.getRelation());
		family.setName(dto.getName());
		family.setSex(dto.getSex());
		family.setBirthday(dto.getBirthday());
		family.setIdNumber(dto.getIdNumber());
		family.setMobile(dto.getMobile());
		family.setPersonNumber(dto.getPersonNumber());
		family.setSocialEnsureNumber(dto.getSocialEnsureNumber());
		family.setCardNumber(dto.getCardNumber());
		family.setCityId(dto.getCityId());
		family.setSiTypeCode(dto.getSiTypeCode());
		family.setChronic(dto.isChronic());
		family.setDef(dto.isDef());
		family.setAuth(dto.isAuth());
		return family;
	}

	public static FamilyQueryDTO toFamilyQueryDTO(Family family) {
		if (family == null) {
			return null;
		}
		FamilyQueryDTO dto = new FamilyQueryDTO();
		dto.setFamilyId(family.getFamilyId());
		dto.setRelation(family.getRelation());
		dto.setName(family.getName());
		dto.setSex(family.getSex());
		dto.setBirthday(family.getBirthday());
		dto.setIdNumber(family.getIdNumber());
		dto.setMobile(family.getMobile());
		dto.setPersonNumber(family.getPersonNumber());
		dto.setSocialEnsureNumber(family.getSocialEnsureNumber());
		dto.setCardNumber(family.getCardNumber());
		dto.setCityId(family.getCityId());
		dto.setSiTypeCode(family.getSiTypeCode());
		dto.setChronic(family.isChronic());
		dto.setDef(family.isDef());
		dto.setAuth(family.isAuth());
		return dto;
	}

	private static FamilyQueryDTO toFamilyQueryDTOWithPerson(Family family, Person person) {
		if (family == null) {
			return null;
		}
		FamilyQueryDTO dto = new FamilyQueryDTO();
		dto.setFamilyId(family.getFamilyId());
		dto.setRelation(family.getRelation());
		dto.setName(family.getName());
		dto.setSex(family.getSex());
		dto.setBirthday(family.getBirthday());
		dto.setIdNumber(family.getIdNumber());
		dto.setMobile(family.getMobile());
		dto.setPersonNumber(person.getPersonNumberString());
		dto.setPersonId(person.getId().toString());
		dto.setSocialEnsureNumber(family.getSocialEnsureNumber());
		dto.setCardNumber(family.getCardNumber());
		dto.setCityId(family.getCityId());
		dto.setSiTypeCode(family.getSiTypeCode());
		dto.setChronic(family.isChronic());
		dto.setDef(family.isDef());
		dto.setAuth(family.isAuth());
		return dto;
	}

	public static List<FamilyQueryDTO> toAssociatedFamilies(Set<Family> families) {
		List<FamilyQueryDTO> dtos = new ArrayList<FamilyQueryDTO>();
		FamilyQueryDTO dto = null;
		for (Family family : families) {
			if (family != null) {
				List<Person> personList = getPersonRepositoryExtend().findAllByCertificate(family.getIdNumber(), family.getName());
				if (personList == null || personList.size() == 0) {
					dto = toFamilyQueryDTO(family);
					dtos.add(dto);
				} else {
					for (Person person : personList) {
						dto = toFamilyQueryDTOWithPerson(family, person);
						dtos.add(dto);
					}
				}
			}
		}
		return dtos;
	}

}
