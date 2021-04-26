package com.neusoft.ehrss.liaoning.processor.thirdparty;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonDTO;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;

/**
 * 实名认证 转换器 T_USER
 * 
 * @author zhou.haidong
 * 
 *
 */
public class PersonInfoAssembler {

    /**
     * 转为DTO对象
     * 
     * @param value
     * @return MenuDTO
     */
    public static TPQueryPersonDTO toDTO(User value) {
        if (value == null) {
            return null;
        }
        TPQueryPersonDTO dto = new TPQueryPersonDTO();
        dto.setIdNumber(value.getIdNumber());
        dto.setName(value.getName());
        dto.setMobile("");
    	if(StringUtils.isNotBlank(value.getMobile())){
    		dto.setMobile(value.getMobile());
    	}
        return dto;
    }
    
    public static TPQueryPersonDTO toDTO(ThinUser value) {
    	if (value == null) {
    		return null;
    	}
    	TPQueryPersonDTO dto = new TPQueryPersonDTO();
    	dto.setIdNumber(value.getIdNumber());
    	dto.setName(value.getName());
    	dto.setMobile("");
    	if(StringUtils.isNotBlank(value.getMobile())){
    		dto.setMobile(value.getMobile());
    	}
    	return dto;
    }

}
