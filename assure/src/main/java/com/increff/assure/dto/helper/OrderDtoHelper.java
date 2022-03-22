package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.OrderData;
import com.increff.assure.model.OrderForm;
import com.increff.assure.pojo.OrderPojo;

/**
 * OrderDtoHelper
 */
public class OrderDtoHelper {

    public static OrderData convert(OrderPojo orderPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setChannelId(orderPojo.getChannelId());
        orderData.setChannelOrderId(orderPojo.getChannelOrderId());
        orderData.setClientId(orderPojo.getClientId());
        orderData.setCustomerId(orderPojo.getCustomerId());
        orderData.setStatus(CommonsHelper.normalize(orderPojo.getStatus().toString()));
        return orderData;
    }

    public static List<OrderData> convert(List<OrderPojo> orderPojos) {
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            orderDatas.add(convert(orderPojo));
        }
        return orderDatas;
    }

    public static OrderPojo convert(OrderForm orderForm, Long internalChannelId) {
        OrderPojo orderPojo = new OrderPojo();
        if (orderForm.getClientId() != null)
            orderPojo.setClientId(orderForm.getClientId());
        if (orderForm.getCustomerId() != null)
            orderPojo.setCustomerId(orderForm.getCustomerId());
        if (orderForm.getChannelId() != null) {
            orderPojo.setChannelId(orderForm.getChannelId());
        } else {
            orderPojo.setChannelId(internalChannelId);
        }
        if (orderForm.getChannelOrderId() != null && !orderForm.getChannelOrderId().isEmpty())
            orderPojo.setChannelOrderId(orderForm.getChannelOrderId());
        return orderPojo;
    }

}
