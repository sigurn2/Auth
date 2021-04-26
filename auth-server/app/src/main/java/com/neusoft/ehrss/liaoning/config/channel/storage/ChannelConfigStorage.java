package com.neusoft.ehrss.liaoning.config.channel.storage;

import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;

public interface ChannelConfigStorage {

	public ChannelDTO getChannelDTO();

	public void setChannelDTO(ChannelDTO channelDTO);

}
