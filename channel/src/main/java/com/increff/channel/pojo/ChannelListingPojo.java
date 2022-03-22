package com.increff.channel.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "channel_channel_listing")
@Getter
@Setter
public class ChannelListingPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = "channel_listing_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "channel_listing_id")
    private Long id;

    private Long channelId;
    private String channelSkuId;
    private Long clientId;
    private Long globalSkuId;

    @Override
    public String toString() {
        return "ChannelListingPojo [channelId=" + channelId + ", channelSkuId=" + channelSkuId
                + ", clientId=" + clientId + ", globalSkuId=" + globalSkuId + ", id=" + id + "]";
    }
}
