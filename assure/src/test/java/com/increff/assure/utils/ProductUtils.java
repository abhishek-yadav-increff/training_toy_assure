package com.increff.assure.utils;

import com.increff.assure.model.ProductForm;

/**
 * ProductUtils
 */
public class ProductUtils {

    public static ProductForm getProductForm(String clientSkuId, Long clientId, String name,
            String brandId, Double mrp, String description) {
        ProductForm productForm = new ProductForm();
        productForm.setClientSkuId(clientSkuId);
        productForm.setClientId(clientId);
        productForm.setName(name);
        productForm.setBrandId(brandId);
        productForm.setMrp(mrp);
        productForm.setDescription(description);
        return productForm;
    }
}
