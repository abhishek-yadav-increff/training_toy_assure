package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.ChannelDtoHelper;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.model.ChannelData;
import com.increff.assure.model.ChannelForm;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.service.ChannelService;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChannelDto
 */
@Service
public class ChannelDto {

    @Autowired
    private ChannelService channelService;

    private static final Logger LOGGER = Logger.getLogger(ChannelDto.class);

    public void add(ChannelForm channelForm) throws ApiException {
        LOGGER.info("In ChannelService:add()");
        LOGGER.info("Form data received: " + channelForm.toString());
        ChannelPojo channelPojo = ChannelDtoHelper.convert(channelForm);
        channelService.add(channelPojo);
    }

    public ChannelData get(Long id) throws ApiException {
        ChannelPojo channelPojo = channelService.get(id);
        return ChannelDtoHelper.convert(channelPojo);
    }

    public List<ChannelData> getAll() throws ApiException {
        List<ChannelPojo> channelPojos = channelService.getAll();
        return ChannelDtoHelper.convert(channelPojos);
    }

    public List<ChannelData> getByQuery(String query) throws ApiException {
        List<ChannelPojo> channelPojos = channelService.getByQuery(CommonsHelper.normalize(query));
        if (channelPojos == null)
            return null;
        return ChannelDtoHelper.convert(channelPojos);
    }
}
