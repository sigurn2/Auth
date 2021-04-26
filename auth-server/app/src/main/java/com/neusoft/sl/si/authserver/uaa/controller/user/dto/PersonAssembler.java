package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.sl.si.authserver.base.domains.person.Person;

/**
 * 用户转换
 * 
 * @author mojf
 *
 */
public class PersonAssembler {

	// private static Logger LOGGER =
	// LoggerFactory.getLogger(UserAssembler.class);

	private PersonAssembler() {
		// hide for utils
	}

	/**
	 * 转换为DTO对象
	 * 
	 * @param value
	 * @return
	 */
	public static PersonInfoDTO toDTO(Person value) {
		if (value == null) {
			return null;
		}
		PersonInfoDTO dto = new PersonInfoDTO();
		dto.setPersonId(value.getId());
		dto.setPersonNumber(value.getPersonNumberString());
		dto.setProfileStatus(value.getProfileStatus());
		dto.setRetireStatus(value.getRetireStatus());
		dto.setSocialSecurityCardNumber(value.getSocialSecurityCardNumber());
		return dto;
	}

	public static List<PersonInfoDTO> toPersonInfoDTOs(List<Person> persons) {
		List<PersonInfoDTO> list = new ArrayList<PersonInfoDTO>();
		for (Person item : persons) {
			if (item != null) {
				list.add(toDTO(item));
			}
		}
		return list;
	}
}
