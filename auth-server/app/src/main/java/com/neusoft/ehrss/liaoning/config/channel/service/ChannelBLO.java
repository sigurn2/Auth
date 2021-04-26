package com.neusoft.ehrss.liaoning.config.channel.service;

import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.config.channel.storage.ChannelConfigStorage;

public interface ChannelBLO {

	public void setChannelConfigStorage(ChannelConfigStorage channelConfigStorage);

	public ChannelConfigStorage getChannelConfigStorage();

	public ChannelDTO getChannelDTO();

}
