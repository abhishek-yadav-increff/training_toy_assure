package com.increff.channel.enums;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum InvoiceEnum {
    CHANNEL, SELF;

    private static Map<String, InvoiceEnum> mapping = new HashMap<>();
    static {
        mapping.put("channel", CHANNEL);
        mapping.put("self", SELF);
    }

    @JsonCreator
    public static InvoiceEnum fromString(String value) {
        return mapping.get(value);
    }
};
