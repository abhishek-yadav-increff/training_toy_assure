package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ProductDto;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.commons.model.ApiException;
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
public class ProductController {


    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        productDto.add(form);
    }

    @ApiOperation(value = "Gets a Product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Long id) throws ApiException {
        return productDto.get(id);
    }

    // @ApiOperation(value = "Gets a Product by Client ID and Client SKU ID")
    // @RequestMapping(path = "/api/product/{clientId}/{clientSkuId}", method = RequestMethod.GET)
    // public ProductData getClientIdClientSkuId(@PathVariable Long clientId,
    // @PathVariable String clientSkuId) throws ApiException {
    // return productDto.getClientIdClientSkuId(clientId, clientSkuId);
    // }


    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAll();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody ProductForm f) throws ApiException {
        productDto.update(id, f);
    }

}
