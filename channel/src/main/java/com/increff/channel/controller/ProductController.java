package com.increff.channel.controller;

import com.increff.channel.dto.ProductDto;
import com.increff.commons.model.ProductData;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductController {


    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Gets a Product by Client ID and Client SKU ID")
    @RequestMapping(path = "/api/product/{clientId}/{clientSkuId}", method = RequestMethod.GET)
    public ProductData getClientIdClientSkuId(@PathVariable Long clientId,
            @PathVariable String clientSkuId) throws ApiException {
        return productDto.getClientIdClientSkuId(clientId, clientSkuId);
    }


}
