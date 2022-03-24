package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

/**
 * OrderItemData
 */
@Getter
@Setter
public class OrderItemData extends OrderItemForm {
    private Long id;
    private Long orderId;
    private Long globalSkuId;
    private Long allocatedQuantity;
    private Long fulfilledQuantity;
}
