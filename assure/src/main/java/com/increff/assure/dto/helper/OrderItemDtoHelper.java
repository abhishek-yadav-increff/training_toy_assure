package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.OrderItemData;
import com.increff.assure.model.OrderItemForm;
import com.increff.assure.pojo.OrderItemPojo;

/**
 * OrderItemDtoHelper
 */
public class OrderItemDtoHelper {

    public static OrderItemPojo convert(OrderItemForm orderItemForm, Long globalSkuId,
            Long orderId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setGlobalSkuId(globalSkuId);
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setOrderQuantity(orderItemForm.getOrderedQuantity());
        orderItemPojo.setSellingPricePerUnit(
                CommonsHelper.normalize(orderItemForm.getSellingPricePerUnit()));
        return orderItemPojo;
    }

    public static OrderItemData convert(OrderItemPojo orderItemPojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setGlobalSkuId(orderItemPojo.getGlobalSkuId());
        orderItemData.setOrderedQuantity(orderItemPojo.getOrderQuantity());
        orderItemData.setAllocatedQuantity(orderItemPojo.getAllocatedQuantity());
        orderItemData.setFulfilledQuantity(orderItemPojo.getFulfilledQuantity());
        orderItemData.setSellingPricePerUnit(orderItemPojo.getSellingPricePerUnit());
        return orderItemData;
    }

    public static List<OrderItemData> convert(List<OrderItemPojo> orderItemPojos) {
        List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemDatas.add(convert(orderItemPojo));
        }
        return orderItemDatas;
    }

}
