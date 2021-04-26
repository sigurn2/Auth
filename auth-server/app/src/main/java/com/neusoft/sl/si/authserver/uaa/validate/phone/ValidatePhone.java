package com.neusoft.sl.si.authserver.uaa.validate.phone;

import org.apache.commons.lang3.Validate;

/**
 * 目前校验比较简单
 * 
 * @author zhou.haidong
 *
 */
public class ValidatePhone {
	
	private static final String regex = "1[3456789]\\d{9}";

	public static boolean verify(String phone) {
		Validate.notBlank(phone, "手机号码不能为空");
		return phone.matches(regex);
	}

}
