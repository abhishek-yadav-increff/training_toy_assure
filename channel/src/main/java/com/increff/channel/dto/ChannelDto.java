package com.increff.channel.dto;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.increff.channel.client.assureClient.ChannelAssureClient;
import com.increff.channel.dto.helper.ChannelDtoHelper;
import com.increff.channel.dto.helper.CommonsHelper;
import com.increff.channel.model.ChannelData;
import com.increff.channel.model.ChannelForm;
import com.increff.channel.pojo.ChannelPojo;
import com.increff.channel.service.ChannelService;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChannelDto
 */
@Service
public class ChannelDto {


    private static final Logger LOGGER = Logger.getLogger(ChannelDto.class);

    public void add(ChannelForm channelForm) throws ApiException {
        LOGGER.info("In ChannelService:add()");
        LOGGER.info("Form data received: " + channelForm.toString());
        // ChannelPojo channelPojo = ChannelDtoHelper.convert(channelForm);
        ChannelAssureClient.addChannel(channelForm);
        // channelService.add(channelPojo);
    }

    public ChannelData get(Long id) throws ApiException {
        return ChannelAssureClient.getChannel(id);
        // ChannelPojo channelPojo = channelService.get(id);
        // return ChannelDtoHelper.convert(channelPojo);
    }

    public List<ChannelData> getAll() throws ApiException {
        // List<ChannelPojo> channelPojos = channelService.getAll();
        List<ChannelData> channelDatas = ChannelAssureClient.getChannels();
        return channelDatas;
    }

    public List<ChannelData> getByQuery(String query) throws ApiException {
        List<ChannelData> channelDatas = ChannelAssureClient.getChannelsByQuery(query);
        return channelDatas;
        // List<ChannelPojo> channelPojos =
        // channelService.getByQuery(CommonsHelper.normalize(query));
        // if (channelPojos == null)
        // return null;
        // return ChannelDtoHelper.convert(channelPojos);
    }
}
