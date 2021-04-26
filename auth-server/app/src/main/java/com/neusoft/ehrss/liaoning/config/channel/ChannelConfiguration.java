package com.neusoft.ehrss.liaoning.config.channel;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.config.channel.service.ChannelBLO;
import com.neusoft.ehrss.liaoning.config.channel.service.ChannelBLOImpl;
import com.neusoft.ehrss.liaoning.config.channel.storage.ChannelInMemoryConfigStorage;

/**
 * ecard channel configuration
 *
 */
@Configuration
@EnableConfigurationProperties(ChannelProperties.class)
public class ChannelConfiguration {

	private ChannelProperties properties;

	private static Map<String, ChannelBLO> channelBLOServices = Maps.newHashMap();

	@Autowired
	public ChannelConfiguration(ChannelProperties properties) {
		this.properties = properties;
	}

	public static Map<String, ChannelBLO> getChannelBLOServices() {
		return channelBLOServices;
	}

	@Bean
	public Object services() {
		channelBLOServices = this.properties.getConfigs().stream().map(a -> {
			ChannelInMemoryConfigStorage configStorage = new ChannelInMemoryConfigStorage();

			ChannelDTO channelDTO = new ChannelDTO();
			channelDTO.setAccessKey(a.getAccessKey());
			channelDTO.setChannelNo(a.getChannelNo());
			channelDTO.setEncryptKey(a.getEncryptKey());
			channelDTO.setSecretKey(a.getSecretKey());

			configStorage.setChannelDTO(channelDTO);

			ChannelBLO service = new ChannelBLOImpl();
			service.setChannelConfigStorage(configStorage);
			return service;
		}).collect(Collectors.toMap(s -> s.getChannelConfigStorage().getChannelDTO().getAccessKey(), a -> a));

		return Boolean.TRUE;
	}

}
