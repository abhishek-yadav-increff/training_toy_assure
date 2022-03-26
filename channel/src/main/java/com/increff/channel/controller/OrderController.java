package com.increff.channel.controller;

import java.util.List;
import com.increff.channel.dto.OrderDto;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderDataChannel;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderXmlForm;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderController {


    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Adds a Order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody OrderForm form) throws ApiException {
        orderDto.add(form);
    }

    @ApiOperation(value = "Gets a Order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable Long id) throws ApiException {
        return orderDto.get(id);
    }


    @ApiOperation(value = "Generate Invoice of an Order by ID")
    @RequestMapping(path = "/api/order/generateinvoice", method = RequestMethod.PUT)
    public void generateInvoice(@RequestBody OrderXmlForm orderXmlForm) throws ApiException {
        System.out.print(orderXmlForm + "received");
        orderDto.generateInvoice(orderXmlForm);
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderDataChannel> getAll() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Gets pdf of order Invoice")
    @RequestMapping(path = "/api/order/download/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[] getPdf(@PathVariable String id) throws ApiException {
        return orderDto.getPdf(id);
    }


}
