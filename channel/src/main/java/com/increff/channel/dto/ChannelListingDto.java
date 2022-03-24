package com.increff.channel.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.channel.client.assureClient.ChannelListingAssureClient;
import com.increff.channel.client.assureClient.ProductAssureClient;
import com.increff.channel.dto.helper.ChannelListingDtoHelper;
import com.increff.channel.dto.helper.CommonsHelper;
import com.increff.commons.model.ChannelListingData;
// import com.increff.commons.model.ChannelListingData;
import com.increff.commons.model.ChannelListingForm;
import com.increff.channel.pojo.ChannelListingPojo;
import com.increff.channel.service.ChannelListingService;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ProductData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChannelListingDto
 */
@Service
public class ChannelListingDto {

    @Autowired
    private ChannelListingAssureClient client;

    // @Autowired
    // private ProductAssureClient productAssureClient;

    // @Autowired
    // private ProductService productService;
    // private static final Logger LOGGER = Logger.getLogger(ChannelListingDto.class);

    public void add(List<ChannelListingForm> channelListingForms) throws ApiException {
        client.addChannelListing(channelListingForms);
    }

    public ChannelListingData get(Long id) throws ApiException {
        return client.getChannelListing(id);
        // ChannelListingPojo channelListingPojo = channelListingService.get(id);
        // return ChannelListingDtoHelper.convert(channelListingPojo);
    }

    public List<ChannelListingData> getAll() throws ApiException {
        return client.getChannelListings();
        // List<ChannelListingPojo> channelListingPojos = channelListingService.getAll();
        // return ChannelListingDtoHelper.convert(channelListingPojos);
    }

}
