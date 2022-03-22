package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * OrderItemForm
 */
@Setter
@Getter
public class OrderItemForm {

    private String clientSkuId;
    private Long orderedQuantity;
    private Double sellingPricePerUnit;
}
