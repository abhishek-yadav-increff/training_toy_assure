package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.BinDto;
import com.increff.assure.model.BinData;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.service.ApiException;
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
public class BinController {


    @Autowired
    private BinDto binDto;

    @ApiOperation(value = "Adds a Bin")
    @RequestMapping(path = "/api/bin", method = RequestMethod.POST)
    @ResponseBody
    public BinIndexRange add(@RequestBody BinForm form) throws ApiException {
        return binDto.add(form);
    }

    // @ApiOperation(value = "Gets a Bin by Bin ID")
    // @RequestMapping(path = "/api/bin/{id}", method = RequestMethod.GET)
    // public BinData get(@PathVariable Long id) throws ApiException {
    // return binDto.get(id);
    // }

    // @ApiOperation(value = "Gets list of all Bins")
    // @RequestMapping(path = "/api/bin", method = RequestMethod.GET)
    // public List<BinData> getAll() throws ApiException {
    // return binDto.getAll();
    // }

}
