package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import com.neusoft.sl.si.authserver.base.domains.user.PersonalUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;

/**
 * @author gaochuan
 * @create 2018-10-30 11:12
 **/
public class AlipayUserDTOAssembler{

    /**
     * DTO 转换成 个人用户实体
     *
     * @param userDTO
     * @return
     */
    public static User crtfromDTO(AlipayUserDTO userDTO) {
        PersonalUser user = new PersonalUser(userDTO.getAccount(), userDTO.getName(), userDTO.getPassword());
        user.setMobile(userDTO.getMobilenumber());
        user.setIdType(userDTO.getIdType());
        user.setIdNumber(userDTO.getIdNumber());
        user.setEmail(userDTO.getEmail());
        user.setSecretQuestion(userDTO.getSecretQuestion());
        return user;
    }

    /**
     * 个人用户实体转换成DTO
     *
     * @param user
     * @return
     */
    public static PersonUserDTO toDTO(User user) {
        PersonUserDTO dto = new PersonUserDTO();
        dto.setAccount(user.getAccount());
        dto.setName(user.getName());
//        dto.setPassword(user.getPassword());
        dto.setMobilenumber(user.getMobile());
        dto.setIdType(user.getIdType());
        dto.setIdNumber(user.getIdNumber());
        return dto;
    }

}
