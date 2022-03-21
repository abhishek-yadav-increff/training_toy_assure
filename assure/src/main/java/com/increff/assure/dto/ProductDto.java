package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.ProductDtoHelper;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ProductService;
import com.increff.common.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductDto
 */
@Service
public class ProductDto {

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductDto.class);

    public void add(ProductForm productForm) throws ApiException {
        LOGGER.info("In ProductService:add()");
        LOGGER.info("Form data received: " + productForm.toString());
        ProductPojo productPojo = ProductDtoHelper.convert(productForm);
        LOGGER.info("Converted pojo data: " + productPojo.toString());
        productService.add(productPojo);
    }

    public ProductData get(Long id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        return ProductDtoHelper.convert(productPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojos = productService.getAll();
        return ProductDtoHelper.convert(productPojos);
    }

    public void update(Long id, ProductForm f) throws ApiException {
        ProductPojo p = ProductDtoHelper.convert(f);
        productService.update(id, p);
    }
}
