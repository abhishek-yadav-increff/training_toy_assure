package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {

    private String clientSkuId;
    private Long clientId;
    private String name;
    private String brandId;
    private Double mrp;
    private String description;

    public ProductForm() {}

    @Override
    public String toString() {
        return "ProductForm [brandId=" + brandId + ", clientId=" + clientId + ", clientSkuId="
                + clientSkuId + ", description=" + description + ", mrp=" + mrp + ", name=" + name
                + "]";
    }

}
