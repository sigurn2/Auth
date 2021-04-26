package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums.ResultCodeEnum;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums.ResultStatusEnum;

public class ResultInfo {

	private String resultStatus;

	private String resultCode;

	private String resultMsg;

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public void setErrorMsg(String msg) {
		this.resultCode = ResultCodeEnum.FAILURE.toString();
		this.resultStatus = ResultStatusEnum.F.toString();
		this.resultMsg = msg;
	}

	public void setMsg(String msg) {
		this.resultCode = ResultCodeEnum.SUCCESS.toString();
		this.resultStatus = ResultStatusEnum.S.toString();
		this.resultMsg = msg;
	}

	public boolean result() {
		if (StringUtils.isNotBlank(this.resultStatus)) {
			return this.resultStatus.equals(ResultStatusEnum.S.toString());
		}
		return false;
	}

}
