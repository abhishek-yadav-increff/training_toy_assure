package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * OrderForm
 */
@Getter
@Setter
public class OrderForm {
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;

    @Override
    public String toString() {
        return "OrderForm [channelId=" + channelId + ", channelOrderId=" + channelOrderId
                + ", clientId=" + clientId + ", customerId=" + customerId + "]";
    }

}
