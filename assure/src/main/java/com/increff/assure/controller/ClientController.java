package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ClientDto;
import com.increff.assure.model.ClientData;
import com.increff.assure.model.ClientForm;
import com.increff.assure.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "Adds a Client")
    @RequestMapping(path = "/api/client", method = RequestMethod.POST)
    public void add(@RequestBody ClientForm form) throws ApiException {
        clientDto.add(form);
    }

    @ApiOperation(value = "Gets a Client by ID")
    @RequestMapping(path = "/api/client/{id}", method = RequestMethod.GET)
    public ClientData get(@PathVariable int id) throws ApiException {
        return clientDto.get(id);
    }

    @ApiOperation(value = "Search a Client by ID and Name")
    @RequestMapping(path = "/api/client/search/", method = RequestMethod.GET)
    public List<ClientData> get(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return clientDto.getByQuery(query);
    }

    @ApiOperation(value = "Gets list of all Clients")
    @RequestMapping(path = "/api/client", method = RequestMethod.GET)
    public List<ClientData> getAll() throws ApiException {
        return clientDto.getAll();
    }

    @ApiOperation(value = "Updates a Client")
    @RequestMapping(path = "/api/client/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ClientForm f) throws ApiException {
        clientDto.update(id, f);
    }

}
