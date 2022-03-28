package com.increff.assure.utils;

import com.increff.assure.model.ChannelListingForm;

/**
 * ChannelListingUtils
 */
public class ChannelListingUtils {

    public static ChannelListingForm getChannelListingForm(Long channelId, String channelSkuId, Long clientId,
            String clientSkuId) {
        ChannelListingForm clf = new ChannelListingForm();
        clf.setChannelId(channelId);
        clf.setChannelSkuId(channelSkuId);
        clf.setClientId(clientId);
        clf.setClientSkuId(clientSkuId);
        return clf;
    }
}