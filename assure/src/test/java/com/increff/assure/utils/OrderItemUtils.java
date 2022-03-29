package com.increff.assure.utils;

import com.increff.commons.model.OrderItemForm;

/**
 * OrderItemUtils
 */
public class OrderItemUtils {

    public static OrderItemForm getOrderItemForm(String clientSkuId, String channelSkuId,
            Long orderedQuantity, Double sellingPricePerUnit) {
        OrderItemForm oif = new OrderItemForm();
        oif.setChannelSkuId(channelSkuId);
        oif.setClientSkuId(clientSkuId);
        oif.setOrderedQuantity(orderedQuantity);
        oif.setSellingPricePerUnit(sellingPricePerUnit);
        return oif;
    }
}
