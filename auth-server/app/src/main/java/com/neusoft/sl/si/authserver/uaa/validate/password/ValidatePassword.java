package com.neusoft.sl.si.authserver.uaa.validate.password;

import org.apache.commons.lang3.Validate;

import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

public class ValidatePassword {
	// 以字母开头
	// 至少包含字母和数字两种组合
	// private static final String regex = "[a-zA-Z]{1}.*\\d+.*";
	// 字母和数字组合
	// private static final String regex =
	// "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
	// private static final String regex = "(?![0-9]+$)(?![a-zA-Z]+$).{8,}";
	// 密码位数要求大于等于8位
	private static final String regex_length = ".{8,}";
	// 包含数字
	private static final String regex_number = ".*\\d+.*";
	// 包含字母
	private static final String regex_letter = ".*[a-zA-Z]+.*";
	// 包含空格
	private static final String regex_space = ".*\\s+.*";

	public static void verifyPassword(String password) {
		Validate.notBlank(password, "请输入密码");
		if (password.matches(regex_space)) {
			throw new BusinessException("密码不能包含空格");
		}
		if (!password.matches(regex_length)) {
			throw new BusinessException("密码至少包含8个字符");
		}
		if (!password.matches(regex_number) || !password.matches(regex_letter)) {
			throw new BusinessException("密码至少包含字母和数字");
		}
	}

}
