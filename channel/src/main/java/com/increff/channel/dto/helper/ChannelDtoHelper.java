package com.increff.channel.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.channel.enums.InvoiceEnum;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ChannelForm;
import com.increff.channel.pojo.ChannelPojo;

/**
 * ChannelDtoHelper
 */
public class ChannelDtoHelper {

    public static ChannelData convert(ChannelPojo channelPojo) {
        ChannelData channelData = new ChannelData();
        channelData.setId(channelPojo.getId());
        channelData.setName(channelPojo.getName());
        channelData
                .setInvoiceType(CommonsHelper.normalize(channelPojo.getInvoiceType().toString()));
        return channelData;
    }

    public static ChannelPojo convert(ChannelForm channelForm) {
        ChannelPojo channelPojo = new ChannelPojo();
        if (channelForm.getName() != null && !channelForm.getName().isEmpty())
            channelPojo.setName(CommonsHelper.normalize(channelForm.getName()));
        if (channelForm.getInvoiceType() != null && !channelForm.getInvoiceType().isEmpty())
            channelPojo.setInvoiceType(
                    InvoiceEnum.fromString(CommonsHelper.normalize(channelForm.getInvoiceType())));
        return channelPojo;
    }

    public static List<ChannelData> convert(List<ChannelPojo> channelPojos) {
        List<ChannelData> channelDatas = new ArrayList<ChannelData>();
        for (ChannelPojo channelPojo : channelPojos) {
            channelDatas.add(convert(channelPojo));
        }
        return channelDatas;
    }

}
