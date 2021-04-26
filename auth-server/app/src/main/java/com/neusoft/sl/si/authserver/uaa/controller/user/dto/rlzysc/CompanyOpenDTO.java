package com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc;

/**
 * 
 * @author mojf
 *
 */
public class CompanyOpenDTO extends OpenUserDTO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 统一信用代码
	 */
	private String orgCode;

	private String account;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
