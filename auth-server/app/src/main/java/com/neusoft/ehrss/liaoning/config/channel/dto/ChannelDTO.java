package com.neusoft.ehrss.liaoning.config.channel.dto;

public class ChannelDTO {

	/**
	 * ak
	 */
	private String accessKey;

	/**
	 * sk
	 */
	private String secretKey;

	/**
	 * 加密密钥
	 */
	private String encryptKey;

	/**
	 * 渠道编号
	 */
	private String channelNo;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

}
