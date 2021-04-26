package com.neusoft.ehrss.liaoning.config.channel.service;

import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.config.channel.storage.ChannelConfigStorage;

public class ChannelBLOImpl implements ChannelBLO {

	protected ChannelConfigStorage channelConfigStorage;

	@Override
	public ChannelDTO getChannelDTO() {
		return channelConfigStorage.getChannelDTO();
	}

	@Override
	public void setChannelConfigStorage(ChannelConfigStorage channelConfigStorage) {
		this.channelConfigStorage = channelConfigStorage;
	}

	@Override
	public ChannelConfigStorage getChannelConfigStorage() {
		return channelConfigStorage;
	}

}
