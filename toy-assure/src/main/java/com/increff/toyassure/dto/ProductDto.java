package com.increff.toyassure.dto;

import java.util.List;
import com.increff.toyassure.dto.helper.ProductDtoHelper;
import com.increff.toyassure.model.ProductData;
import com.increff.toyassure.model.ProductForm;
import com.increff.toyassure.pojo.ProductPojo;
import com.increff.toyassure.service.ApiException;
import com.increff.toyassure.service.ProductService;
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
