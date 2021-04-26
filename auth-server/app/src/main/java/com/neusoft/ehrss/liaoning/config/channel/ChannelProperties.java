package com.neusoft.ehrss.liaoning.config.channel;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.neusoft.sl.girder.utils.JsonMapper;

/**
 * ecard channel properties
 *
 */
@ConfigurationProperties(prefix = "saber.ecard.channel")
public class ChannelProperties {

	private List<ChannelConfig> configs;

	public static class ChannelConfig {
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

	@Override
	public String toString() {
		return JsonMapper.toJson(this);
	}

	public List<ChannelConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ChannelConfig> configs) {
		this.configs = configs;
	}

}
