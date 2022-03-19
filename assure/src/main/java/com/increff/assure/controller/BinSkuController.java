package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.BinSkuDto;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BinSkuController {


    @Autowired
    private BinSkuDto binDto;

    @ApiOperation(value = "Adds a BinSku")
    @RequestMapping(path = "/api/binSku", method = RequestMethod.POST)
    public void add(@RequestBody List<BinSkuForm> forms) throws ApiException {
        binDto.add(forms);
    }

    @ApiOperation(value = "Gets a BinSku by BinSku ID")
    @RequestMapping(path = "/api/binSku/{id}", method = RequestMethod.GET)
    public BinSkuData get(@PathVariable Long id) throws ApiException {
        return binDto.get(id);
    }

    @ApiOperation(value = "Gets a BinSku by globalSkuId")
    @RequestMapping(path = "/api/binSku/globalskuid/{id}", method = RequestMethod.GET)
    public List<BinSkuData> getByGlobalSkuId(@PathVariable Long id) throws ApiException {
        return binDto.getByGlobalSkuId(id);
    }

    @ApiOperation(value = "Gets list of all BinSkus")
    @RequestMapping(path = "/api/binSku", method = RequestMethod.GET)
    public List<BinSkuData> getAll() throws ApiException {
        return binDto.getAll();
    }

}
