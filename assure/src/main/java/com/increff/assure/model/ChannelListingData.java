package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelListingData extends ChannelListingForm {

    private Long id;
    private Long globalSkuId;

    @Override
    public String toString() {
        return "ChannelListingData [globalSkuId=" + globalSkuId + ", id=" + id + ", channelId="
                + getChannelId() + ", channelSkuId=" + getChannelSkuId() + ", clientId="
                + getClientId() + ", clientSkuId=" + getClientSkuId() + "]";
    }
}
