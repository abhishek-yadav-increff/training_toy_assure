package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;

import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderDataChannel;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderItemXmlForm;
import com.increff.commons.model.OrderXmlForm;

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
        orderData.setStatus(orderPojo.getStatus().toString());
        return orderData;
    }

    public static List<OrderData> convert(List<OrderPojo> orderPojos) {
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            orderDatas.add(convert(orderPojo));
        }
        return orderDatas;
    }

    public static OrderPojo convert(OrderForm orderForm) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setClientId(orderForm.getClientId());
        orderPojo.setCustomerId(orderForm.getCustomerId());
        orderPojo.setChannelId(orderForm.getChannelId());
        if (orderForm.getChannelOrderId() != null && !orderForm.getChannelOrderId().isEmpty())
            orderPojo.setChannelOrderId(orderForm.getChannelOrderId().trim());
        return orderPojo;
    }

    public static OrderDataChannel convertForChannel(OrderPojo orderPojo, String clientName,
            String customerName, String channelName) {
        OrderDataChannel orderDataChannel = new OrderDataChannel(convert(orderPojo), clientName, customerName,
                channelName);
        return orderDataChannel;
    }

    public static OrderXmlForm convert(OrderPojo p, List<OrderItemXmlForm> orderItemXmlForms,
            String clientName, String customerName, String channelName) {
        OrderXmlForm orderXmlForm = new OrderXmlForm(convert(p), clientName, customerName, channelName);
        orderXmlForm.setItems(orderItemXmlForms);
        Double totalCost = 0D;
        for (OrderItemXmlForm oi : orderItemXmlForms) {
            totalCost += Double.parseDouble(oi.getTotalSellingPrice());
        }
        orderXmlForm.setTotal(CommonsHelper.doubleToString(totalCost));
        return orderXmlForm;
    }

}
