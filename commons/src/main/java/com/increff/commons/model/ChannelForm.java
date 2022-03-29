package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelForm {

    private String name;
    private String invoiceType;


    public ChannelForm() {}


    @Override
    public String toString() {
        return "ChannelForm [invoiceType=" + invoiceType + ", name=" + name + "]";
    }


}
