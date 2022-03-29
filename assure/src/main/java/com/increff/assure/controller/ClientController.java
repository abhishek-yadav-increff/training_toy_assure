package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ClientDto;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation(value = "Adds a User")
    @RequestMapping(path = "/api/client", method = RequestMethod.POST)
    public void add(@RequestBody ClientForm form) throws ApiException {
        clientDto.add(form);
    }

    // @ApiOperation(value = "Gets a User by ID")
    // @RequestMapping(path = "/api/client/{id}", method = RequestMethod.GET)
    // public ClientData get(@PathVariable Long id) throws ApiException {
    // return clientDto.get(id);
    // }

    @ApiOperation(value = "Gets list of all User")
    @RequestMapping(path = "/api/client/all", method = RequestMethod.GET)
    public List<ClientData> getAll() throws ApiException {
        return clientDto.getAll();
    }

    // @ApiOperation(value = "Search a Client by ID and Name")
    // @RequestMapping(path = "/api/client/search/", method = RequestMethod.GET)
    // public List<ClientData> get(@RequestParam(value = "term", required = false) String query,
    // @RequestParam(value = "type", required = true) String type) throws ApiException {
    // return clientDto.getByQuery(query);
    // }

    @ApiOperation(value = "Search a Client by ID and Name")
    @RequestMapping(path = "/api/client/", method = RequestMethod.GET)
    public List<ClientData> getClient(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQueryClient(query);
    }

    @ApiOperation(value = "Search a Customer by ID and Name")
    @RequestMapping(path = "/api/client/customer/", method = RequestMethod.GET)
    public List<ClientData> getCustomer(
            @RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQueryCustomer(query);
    }

}
