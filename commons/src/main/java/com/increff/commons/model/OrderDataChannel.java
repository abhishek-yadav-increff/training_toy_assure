package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

/**
 * OrderDataChannel
 */
@Getter
@Setter
public class OrderDataChannel {

    private String clientName;
    private String channelName;
    private String customerName;
    private String channelOrderId;
    private String status;

    public OrderDataChannel(OrderData orderData, String clientName, String channelName,
            String customerName) {
        this.clientName = clientName;
        this.channelName = channelName;
        this.customerName = customerName;
        this.channelOrderId = orderData.getChannelOrderId();
        this.status = orderData.getStatus();
    }

    public OrderDataChannel() {}

    @Override
    public String toString() {
        return "OrderDataChannel [channelName=" + channelName + ", channelOrderId=" + channelOrderId
                + ", clientName=" + clientName + ", customerName=" + customerName + ", status="
                + status + "]";
    }
}
