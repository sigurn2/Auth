package com.neusoft.ehrss.liaoning.config.channel.storage;

import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;

public class ChannelInMemoryConfigStorage implements ChannelConfigStorage {

	protected ChannelDTO channelDTO;

	@Override
	public ChannelDTO getChannelDTO() {
		return channelDTO;
	}

	@Override
	public void setChannelDTO(ChannelDTO channelDTO) {
		this.channelDTO = channelDTO;
	}

}
