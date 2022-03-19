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

    public static OrderPojo convert(OrderForm orderForm) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setClientId(orderForm.getClientId());
        orderPojo.setCustomerId(orderForm.getCustomerId());
        orderPojo.setChannelId(orderForm.getChannelId());
        orderPojo.setChannelOrderId(orderForm.getChannelOrderId());
        return orderPojo;
    }

    public static List<OrderData> convert(List<OrderPojo> orderPojos) {
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            orderDatas.add(convert(orderPojo));
        }
        return orderDatas;
    }

    public static List<OrderPojo> convertForms(List<OrderForm> orderForms) {
        List<OrderPojo> orderPojos = new ArrayList<>();
        for (OrderForm orderForm : orderForms) {
            orderPojos.add(convert(orderForm));
        }
        return orderPojos;
    }

}
