package com.increff.channel.dto;

import java.util.List;
import com.increff.channel.client.assureClient.ChannelAssureClient;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChannelDto
 */
@Service
public class ChannelDto {

    @Autowired
    private ChannelAssureClient channelAssureClient;

    public List<ChannelData> getByQuery(String query) throws ApiException {
        List<ChannelData> channelDatas = channelAssureClient.getChannelsByQuery(query);
        return channelDatas;
    }
}
