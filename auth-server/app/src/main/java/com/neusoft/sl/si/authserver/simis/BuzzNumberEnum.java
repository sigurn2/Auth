package com.neusoft.sl.si.authserver.simis;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * 与外部板块业务交易代码定义
 * 
 * @author mojf
 * @version 1.0
 */
@XmlEnum(String.class)
public enum BuzzNumberEnum {

	/** 业务交易代码范围 */
	@XmlEnumValue("NB_SBXT_QYWB_001")
	单位新开户("NB_SBXT_QYWB_001", true), @XmlEnumValue("NB_SBXT_QYWB_002")
	人员实名查询("NB_SBXT_QYWB_002", false), @XmlEnumValue("NB_SBXT_QYWB_003")
	人员实名认证("NB_SBXT_QYWB_003", true), @XmlEnumValue("NB_SBXT_QYWB_004")
	单位用户激活("NB_SBXT_QYWB_004", true), @XmlEnumValue("NB_SBXT_QYWB_005")
	根据主单位编号查询关联单位("NB_SBXT_QYWB_005", false), @XmlEnumValue("NB_SBXT_QYWB_006")
	维护关联单位("NB_SBXT_QYWB_006", true), @XmlEnumValue("NB_SBXT_QYWB_007")
	修改CA操作类型("NB_SBXT_QYWB_007", true), @XmlEnumValue("NB_SBXT_QYWB_008")
	CA注销("NB_SBXT_QYWB_008", true), @XmlEnumValue("NB_SBXT_QYWB_009")
	个人柜台注册("NB_SBXT_QYWB_009", true), @XmlEnumValue("NB_SBXT_QYWB_010")
	根据单位编号查询网厅开通情况("NB_SBXT_QYWB_010", true), @XmlEnumValue("NB_SBXT_QYWB_011")
	个人修改手机号码("NB_SBXT_QYWB_011", true), @XmlEnumValue("NB_SBXT_QYWB_012")
	第三方注册("NB_SBXT_QYWB_012", true), @XmlEnumValue("NB_SBXT_QYWB_013")
	企业密码重置("NB_SBXT_QYWB_013", true), @XmlEnumValue("NB_CAXT_QYWB_001")
	签约请求("NB_CAXT_QYWB_001", true), @XmlEnumValue("NB_CAXT_QYWB_002")
	修改请求("NB_CAXT_QYWB_002", true), @XmlEnumValue("NB_CAXT_QYWB_003")
	单户查询请求社保代码("NB_CAXT_QYWB_003", false), @XmlEnumValue("NB_CAXT_QYWB_004")
	单户查询请求组织机构代码("NB_CAXT_QYWB_004", false), @XmlEnumValue("NB_CAXT_QYWB_005")
	取消ca用户("NB_CAXT_QYWB_005", false), @XmlEnumValue("NB_CAXT_QYWB_014")
	企业维护角色类型("NB_CAXT_QYWB_014", false), @XmlEnumValue("NB_TPXT_QYWB_001")
	第三方个人账号注册("NB_TPXT_QYWB_001", true), @XmlEnumValue("NB_TPXT_QYWB_002")
	第三方企业账号注册("NB_TPXT_QYWB_002", true), @XmlEnumValue("NB_TPXT_QYWB_003")
	第三方密码修改("NB_TPXT_QYWB_003", true), @XmlEnumValue("NB_TPXT_QYWB_004")
	第三方敏感信息重置("NB_TPXT_QYWB_004", true), @XmlEnumValue("NB_TPXT_QYWB_005")
	第三方个人用户信息查询("NB_TPXT_QYWB_005", false), @XmlEnumValue("NB_TPXT_QYWB_006")
	第三方个人用户社保信息查询("NB_TPXT_QYWB_006", false),@XmlEnumValue("GGFW_LN_GGYW_037")
	单位绑定接口("GGFW_LN_GGYW_037",false), @XmlEnumValue("CJWT_LNCJ_003")
	城居人员基础信息查询("CJWT_LNCJ_003",false), @XmlEnumValue("CJWT_LNCJ_003")
	单位基本信息查询("GGFW_LN_GGYW_QYYL_CBDJ_001",false),@XmlEnumValue("GGFW_LN_GGYW_QYYL_CBDJ_001")
	职工人员基本信息查询("GGFW_LN_GRWT_001",false),@XmlEnumValue("GGFW_LN_GRWT_033")
	职工人员基本信息查询无过滤("GGFW_LN_GRWT_033",false);

	/** 代码值 */
	private String value;

	/** 是否是业务交易 */
	private boolean transation;

	/** String Map */
	private static Map<String, BuzzNumberEnum> valueMap = new HashMap<String, BuzzNumberEnum>();

	/** 初始化代码表 */
	static {
		for (BuzzNumberEnum enumItem : BuzzNumberEnum.values()) {
			valueMap.put(enumItem.value, enumItem);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param value
	 *            代码值
	 * @param isQuery
	 *            是否业务类交易
	 */
	BuzzNumberEnum(String value, boolean transation) {
		this.value = value;
		this.transation = transation;
	}

	/**
	 * 获取enum对象
	 */
	public static BuzzNumberEnum getEnum(String value) {
		return valueMap.get(value);
	}

	/**
	 * 存储enum值
	 */
	public String toString() {
		return value;
	}

	/**
	 * @return the query
	 */
	public boolean isTransation() {
		return transation;
	}

}
