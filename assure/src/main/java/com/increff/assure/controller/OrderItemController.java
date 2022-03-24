package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.OrderItemDto;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.OrderItemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemController {


    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Gets a OrderItem by ID")
    @RequestMapping(path = "/api/orderitem/{orderId}", method = RequestMethod.GET)
    public List<OrderItemData> get(@PathVariable Long orderId) throws ApiException {
        return orderItemDto.getByOrderId(orderId);
    }



}
