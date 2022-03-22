package com.increff.channel.dto;

import java.util.List;
import com.increff.channel.client.assureClient.OrderAssureClient;
import com.increff.channel.model.OrderData;
import com.increff.channel.model.OrderForm;
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
    private OrderAssureClient orderAssureClient;

    private static final Logger LOGGER = Logger.getLogger(OrderDto.class);

    public void add(OrderForm orderForm) throws ApiException {
        LOGGER.info("In OrderService:add()");
        LOGGER.info("Form data received: " + orderForm.toString());
    }

    public OrderData get(Long id) throws ApiException {
        return orderAssureClient.getOrder(id);
    }

    public List<OrderData> getAll() throws ApiException {
        return orderAssureClient.getOrders();
    }

    // public void update(Long id, OrderForm f) throws ApiException {
    // OrderPojo p = OrderDtoHelper.convert(f);
    // orderService.update(id, p);
    // }

    public void add(List<OrderForm> orderForms) {
        orderAssureClient.addBatchOrder(orderForms);
    }
}
