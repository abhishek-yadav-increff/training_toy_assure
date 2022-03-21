package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_channellisting")
@Getter
@Setter
public class ChannelListingPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = "channellisting_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "channellisting_id")
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
