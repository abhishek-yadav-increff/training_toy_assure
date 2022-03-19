package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.dto.helper.ChannelListingDtoHelper;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.model.ChannelListingData;
// import com.increff.assure.model.ChannelListingData;
import com.increff.assure.model.ChannelListingForm;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.ChannelListingService;
import com.increff.assure.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChannelListingDto
 */
@Service
public class ChannelListingDto {

    @Autowired
    private ChannelListingService channelListingService;

    @Autowired
    private ProductService productService;
    private static final Logger LOGGER = Logger.getLogger(ChannelListingDto.class);

    public void add(List<ChannelListingForm> channelListingForms) throws ApiException {
        LOGGER.info("In ChannelListingService:add()");
        LOGGER.info("Form data received: " + channelListingForms.toString());
        List<ChannelListingPojo> channelListingPojos = new ArrayList<ChannelListingPojo>();
        for (ChannelListingForm chLiForm : channelListingForms) {

            ProductPojo productPojo = productService.getClientIdClientSkuId(chLiForm.getClientId(),
                    CommonsHelper.normalize(chLiForm.getClientSkuId()));
            ChannelListingPojo channelListingPojo =
                    ChannelListingDtoHelper.convert(chLiForm, productPojo);
            channelListingPojos.add(channelListingPojo);
        }
        channelListingService.add(channelListingPojos);
    }

    public ChannelListingData get(int id) throws ApiException {
        ChannelListingPojo channelListingPojo = channelListingService.get(id);
        return ChannelListingDtoHelper.convert(channelListingPojo);
    }

    public List<ChannelListingData> getAll() throws ApiException {
        List<ChannelListingPojo> channelListingPojos = channelListingService.getAll();
        return ChannelListingDtoHelper.convert(channelListingPojos);
    }

    public void update(int id, ChannelListingForm channelListingForm) throws ApiException {
        ProductPojo productPojo =
                productService.getClientIdClientSkuId(channelListingForm.getClientId(),
                        CommonsHelper.normalize(channelListingForm.getClientSkuId()));
        ChannelListingPojo p = ChannelListingDtoHelper.convert(channelListingForm, productPojo);
        channelListingService.update(id, p);
    }

}
