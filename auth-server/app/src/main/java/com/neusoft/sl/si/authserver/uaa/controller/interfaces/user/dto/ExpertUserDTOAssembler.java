/**
 * 
 */
package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import com.neusoft.sl.si.authserver.base.domains.user.ExpertUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;

/**
 * 专家用户转换器
 * 
 * @author mojf
 *
 */
public class ExpertUserDTOAssembler {

	/**
	 * DTO 转换成 个人用户实体
	 * 
	 * @param userDTO
	 * @return
	 */
	public static User crtfromDTO(PersonUserDTO userDTO) {
		ExpertUser user = new ExpertUser(userDTO.getAccount(), userDTO.getName(), userDTO.getPassword());
		user.setExpertAccount(userDTO.getIdNumber());
		return user;
	}

	/**
	 * 个人用户实体转换成DTO
	 * 
	 * @param user
	 * @return
	 */
	/*public static PersonUserDTO toDTO(User user) {
		PersonUserDTO dto = new PersonUserDTO();
		dto.setAccount(user.getAccount());
		dto.setName(user.getName());
		// dto.setPassword(user.getPassword());
		dto.setMobilenumber(user.getMobile());
		dto.setIdType(user.getIdType());
		dto.setIdNumber(user.getIdNumber());
		return dto;
	}*/

}
