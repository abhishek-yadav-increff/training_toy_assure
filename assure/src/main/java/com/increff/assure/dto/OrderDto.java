package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.OrderDtoHelper;
import com.increff.assure.model.OrderData;
import com.increff.assure.model.OrderForm;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.service.OrderService;
import com.increff.common.model.ApiException;
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

    private static final Logger LOGGER = Logger.getLogger(OrderDto.class);

    public void add(OrderForm orderForm) throws ApiException {
        LOGGER.info("In OrderService:add()");
        LOGGER.info("Form data received: " + orderForm.toString());
        OrderPojo orderPojo = OrderDtoHelper.convert(orderForm);
        orderService.add(orderPojo);
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo orderPojo = orderService.get(id);
        return OrderDtoHelper.convert(orderPojo);
    }

    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> orderPojos = orderService.getAll();
        return OrderDtoHelper.convert(orderPojos);
    }

    public void update(int id, OrderForm f) throws ApiException {
        OrderPojo p = OrderDtoHelper.convert(f);
        orderService.update(id, p);
    }

    public void add(List<OrderForm> orderForms) {
        List<OrderPojo> orderPojos = OrderDtoHelper.convertForms(orderForms);
        orderService.add(orderPojos);
    }
}
