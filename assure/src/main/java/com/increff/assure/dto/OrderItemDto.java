package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.dto.helper.OrderItemDtoHelper;
import com.increff.assure.model.OrderItemData;
import com.increff.assure.model.OrderItemForm;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.OrderItemService;
import com.increff.assure.service.OrderService;
import com.increff.assure.service.ProductService;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderItemDto
 */
@Service
public class OrderItemDto {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public void add(OrderItemForm orderItemForm, Long orderId) throws ApiException {
        OrderPojo orderPojo = orderService.get(orderId);
        ProductPojo productPojo = productService.getClientIdClientSkuId(orderPojo.getClientId(),
                CommonsHelper.normalize((orderItemForm.getClientSkuId())));
        OrderItemPojo orderItemPojo =
                OrderItemDtoHelper.convert(orderItemForm, productPojo.getGlobalSkuId(), orderId);
        orderItemService.add(orderItemPojo);
    }

    public OrderItemData get(Long id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemService.get(id);
        return OrderItemDtoHelper.convert(orderItemPojo);
    }


    public List<OrderItemData> getAll() throws ApiException {
        List<OrderItemPojo> orderItemPojos = orderItemService.getAll();
        return OrderItemDtoHelper.convert(orderItemPojos);
    }

}
