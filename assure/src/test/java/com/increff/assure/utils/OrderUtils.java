package com.increff.assure.utils;

import java.util.List;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderItemForm;

/**
 * OrderUtils
 */
public class OrderUtils {

    public static OrderForm getOrderForm(Long clientId, Long customerId, Long channelId,
            String channelOrderId, List<OrderItemForm> orderItemForms) {
        OrderForm orderForm = new OrderForm();
        orderForm.setChannelId(channelId);
        orderForm.setChannelOrderId(channelOrderId);
        orderForm.setClientId(clientId);
        orderForm.setCustomerId(customerId);
        orderForm.setOrderItemForms(orderItemForms);
        return orderForm;
    }
}
