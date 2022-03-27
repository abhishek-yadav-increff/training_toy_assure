package com.increff.assure.utils;

import com.increff.commons.model.ChannelForm;

/**
 * ChannelUtils
 */
public class ChannelUtils {

    public static ChannelForm getChannelForm(String name, String invoiceType) {
        ChannelForm channelForm = new ChannelForm();
        channelForm.setName(name);
        channelForm.setInvoiceType(invoiceType);
        return channelForm;
    }

}
