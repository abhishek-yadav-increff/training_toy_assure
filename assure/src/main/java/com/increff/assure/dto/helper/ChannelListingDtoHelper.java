package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.ChannelListingData;
import com.increff.assure.model.ChannelListingForm;
import com.increff.assure.pojo.ChannelListingPojo;

/**
 * ChannelListingDtoHelper
 */
public class ChannelListingDtoHelper {

    public static ChannelListingData convert(ChannelListingPojo channelListingPojo) {
        ChannelListingData channelListingData = new ChannelListingData();
        channelListingData.setChannelId(channelListingPojo.getChannelId());
        channelListingData.setChannelSkuId(channelListingPojo.getChannelSkuId());
        channelListingData.setClientId(channelListingPojo.getClientId());
        channelListingData.setGlobalSkuId(channelListingPojo.getGlobalSkuId());
        return channelListingData;
    }

    public static ChannelListingPojo convert(ChannelListingForm channelListingForm,
            Long globalSkuId) {
        ChannelListingPojo channelListingPojo = new ChannelListingPojo();
        String channelSkuId = channelListingForm.getChannelSkuId().trim();
        if (channelSkuId != null && !channelSkuId.isEmpty())
            channelListingPojo.setChannelSkuId(channelSkuId);
        channelListingPojo.setChannelId(channelListingForm.getChannelId());
        channelListingPojo.setClientId(channelListingForm.getClientId());
        channelListingPojo.setGlobalSkuId(globalSkuId);

        return channelListingPojo;
    }

    public static List<ChannelListingData> convert(List<ChannelListingPojo> channelListingPojos) {
        List<ChannelListingData> channelListingDatas = new ArrayList<ChannelListingData>();
        for (ChannelListingPojo channelListingPojo : channelListingPojos) {
            channelListingDatas.add(convert(channelListingPojo));
        }
        return channelListingDatas;
    }

}
