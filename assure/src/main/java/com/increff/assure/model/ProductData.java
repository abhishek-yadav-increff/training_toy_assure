package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends ProductForm {

    private Long globalSkuId;

    @Override
    public String toString() {
        return "ProductData [globalSkuId=" + globalSkuId + ", brandId=" + getBrandId()
                + ", clientId=" + getClientId() + ", clientSkuId=" + getClientSkuId()
                + ", description=" + getDescription() + ", mrp=" + getMrp() + ", name=" + getName()
                + "]";
    }

}
