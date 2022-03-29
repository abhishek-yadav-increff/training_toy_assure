package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.OrderDto;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderDataChannel;
import com.increff.commons.model.OrderForm;
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

    @ApiOperation(value = "Adds an Order from external app")
    @RequestMapping(path = "/api/order/channel", method = RequestMethod.POST)
    public void addForChannel(@RequestBody OrderForm form) throws ApiException {
        orderDto.addForChannel(form);
    }


    // @ApiOperation(value = "Adds a Order")
    // @RequestMapping(path = "/api/order/batch", method = RequestMethod.POST)
    // public void addBatch(@RequestBody List<OrderForm> forms) throws ApiException {
    // System.out.print(forms);
    // orderDto.add(forms);
    // }

    @ApiOperation(value = "Gets an Order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable Long id) throws ApiException {
        return orderDto.get(id);
    }



    @ApiOperation(value = "Allocates an Order by ID")
    @RequestMapping(path = "/api/order/allocate/{id}", method = RequestMethod.PUT)
    public void allocate(@PathVariable Long id) throws ApiException {
        orderDto.allocate(id);
    }

    @ApiOperation(value = "Generates Invoice of an Order by ID")
    @RequestMapping(path = "/api/order/generateinvoice/{id}", method = RequestMethod.PUT)
    public void generateInvoice(@PathVariable Long id) throws ApiException {
        orderDto.generateInvoice(id);
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Gets list of all Orders for external app")
    @RequestMapping(path = "/api/order/channel", method = RequestMethod.GET)
    public List<OrderDataChannel> getAllForChannel() throws ApiException {
        return orderDto.getAllForChannel();
    }

    @ApiOperation(value = "Gets pdf of order Invoice")
    @RequestMapping(path = "/api/order/download/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[] getPdf(@PathVariable Long id) throws ApiException {
        return orderDto.getPdf(id);
    }


    // @ApiOperation(value = "Updates a Order")
    // @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    // public void update(@PathVariable Long id, @RequestBody OrderForm f) throws ApiException {
    // orderDto.update(id, f);
    // }

}
