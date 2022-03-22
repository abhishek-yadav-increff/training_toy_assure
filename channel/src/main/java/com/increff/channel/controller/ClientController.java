package com.increff.channel.controller;

import java.util.List;
import com.increff.channel.dto.ClientDto;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ClientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ClientController {

    @Autowired
    private ClientDto clientDto;

    @ApiOperation(value = "Search a Client by ID and Name")
    @RequestMapping(path = "/api/client/search/", method = RequestMethod.GET)
    public List<ClientData> get(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQuery(query);
    }

    @ApiOperation(value = "Search a Client by ID and Name")
    @RequestMapping(path = "/api/client/client/", method = RequestMethod.GET)
    public List<ClientData> getClient(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQueryClient(query);
    }

    @ApiOperation(value = "Search a Client by ID and Name")
    @RequestMapping(path = "/api/client/customer/", method = RequestMethod.GET)
    public List<ClientData> getCustomer(
            @RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQueryCustomer(query);
    }

}
