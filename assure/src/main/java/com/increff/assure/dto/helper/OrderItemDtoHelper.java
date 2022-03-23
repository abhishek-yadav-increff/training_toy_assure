package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.OrderItemData;
import com.increff.assure.model.OrderItemForm;
import com.increff.assure.model.OrderItemXmlForm;
import com.increff.assure.pojo.OrderItemPojo;

/**
 * OrderItemDtoHelper
 */
public class OrderItemDtoHelper {

    public static OrderItemPojo convert(OrderItemForm orderItemForm, Long globalSkuId,
            Long orderId) {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setGlobalSkuId(globalSkuId);
        orderItemPojo.setOrderedQuantity(orderItemForm.getOrderedQuantity());
        if (orderItemForm.getSellingPricePerUnit() != null)
            orderItemPojo.setSellingPricePerUnit(
                    CommonsHelper.normalize(orderItemForm.getSellingPricePerUnit()));
        return orderItemPojo;
    }

    public static OrderItemData convert(OrderItemPojo orderItemPojo) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setGlobalSkuId(orderItemPojo.getGlobalSkuId());
        orderItemData.setOrderedQuantity(orderItemPojo.getOrderedQuantity());
        orderItemData.setAllocatedQuantity(orderItemPojo.getAllocatedQuantity());
        orderItemData.setFulfilledQuantity(orderItemPojo.getFulfilledQuantity());
        orderItemData.setSellingPricePerUnit(orderItemPojo.getSellingPricePerUnit());
        return orderItemData;
    }

    public static OrderItemXmlForm convertToForm(OrderItemPojo orderItemPojo) {
        OrderItemXmlForm orderItemXmlForm = new OrderItemXmlForm();
        orderItemXmlForm.setGlobalSkuId(orderItemPojo.getGlobalSkuId());
        orderItemXmlForm.setFulfilledQuantity(orderItemPojo.getFulfilledQuantity());
        orderItemXmlForm.setSellingPricePerUnit(
                CommonsHelper.doubleToString(orderItemPojo.getSellingPricePerUnit()));
        orderItemXmlForm.setTotalSellingPrice(
                CommonsHelper.doubleToString(orderItemPojo.getTotalItemCost()));
        return orderItemXmlForm;
    }

    public static List<OrderItemData> convert(List<OrderItemPojo> orderItemPojos) {
        List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemDatas.add(convert(orderItemPojo));
        }
        return orderItemDatas;
    }

}
