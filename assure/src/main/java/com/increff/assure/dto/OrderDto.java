package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.dto.helper.OrderDtoHelper;
import com.increff.assure.dto.helper.OrderItemDtoHelper;
import com.increff.assure.model.OrderData;
import com.increff.assure.model.OrderForm;
import com.increff.assure.model.OrderItemForm;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.OrderItemService;
import com.increff.assure.service.OrderService;
import com.increff.assure.service.ProductService;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDto
 */
@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    private static final Logger LOGGER = Logger.getLogger(OrderDto.class);

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException {
        Long internalChannelId = channelService.getByName("internal").getId();
        OrderPojo orderPojo = OrderDtoHelper.convert(orderForm, internalChannelId);
        orderService.add(orderPojo);
        for (OrderItemForm orderItemForm : orderForm.getOrderItemForms()) {
            Long globalSkuId = productService
                    .getClientIdClientSkuId(orderPojo.getClientId(),
                            CommonsHelper.normalize(orderItemForm.getClientSkuId()))
                    .getGlobalSkuId();
            orderItemService
                    .add(OrderItemDtoHelper.convert(orderItemForm, globalSkuId, orderPojo.getId()));
        }
    }

    public OrderData get(Long id) throws ApiException {
        OrderPojo orderPojo = orderService.get(id);
        return OrderDtoHelper.convert(orderPojo);
    }

    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> orderPojos = orderService.getAll();
        return OrderDtoHelper.convert(orderPojos);
    }

    public void allocate(Long id) {
        orderService.allocate(id);
    }

    // public void update(Long id, OrderForm f) throws ApiException {
    // OrderPojo p = OrderDtoHelper.convert(f);
    // orderService.update(id, p);
    // }


}
