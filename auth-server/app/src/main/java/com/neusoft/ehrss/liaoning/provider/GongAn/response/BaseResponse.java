package com.neusoft.ehrss.liaoning.provider.GongAn.response;

public class BaseResponse {
	/**
	 * Success ：表示成功 ：表示成功 Fail ：表示失败
	 */
	private String resultCode;

	private String resultMessage;

	private String errCode;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
