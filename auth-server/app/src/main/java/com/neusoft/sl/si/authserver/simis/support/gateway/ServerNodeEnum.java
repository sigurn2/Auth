package com.neusoft.sl.si.authserver.simis.support.gateway;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * server_node 枚举类
 * 
 * @author lichs
 *
 */
public enum ServerNodeEnum {

	城乡居民("CXJM"), 铁岭("TIELING"), 社保("Si"),城镇职工("002");

	/** 代码值 */
	private String value;

	/** String Map */
	private static Map<String, ServerNodeEnum> valueMap = new HashMap<String, ServerNodeEnum>();

	/** 初始化代码表 */
	static {
		for (ServerNodeEnum _enum : ServerNodeEnum.values()) {
			valueMap.put(_enum.value, _enum);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param value
	 */
	ServerNodeEnum(String value) {
		this.value = value;
	}

	/**
	 * 获取enum对象
	 * 
	 */
	public static ServerNodeEnum getEnum(String value) {
		return valueMap.get(value);
	}

	/**
	 * 存储enum值
	 * 
	 */
	public String toString() {
		return value;
	}

}
