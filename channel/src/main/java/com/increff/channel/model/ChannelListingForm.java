package com.increff.channel.model;

import lombok.Getter;
import lombok.Setter;

/**
 * ChannelListingForm
 */
@Setter
@Getter
public class ChannelListingForm {
    private Long channelId;
    private String channelSkuId;
    private Long clientId;
    private String clientSkuId;

    @Override
    public String toString() {
        return "ChannelListingForm [channelId=" + channelId + ", channelSkuId=" + channelSkuId
                + ", clientId=" + clientId + ", clientSkuId=" + clientSkuId + "]";
    }
}
