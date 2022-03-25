package com.increff.commons.enums;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserEnum {
    CLIENT, CUSTOMER;

    private static Map<String, UserEnum> mapping = new HashMap<>();
    static {
        mapping.put("client", CLIENT);
        mapping.put("customer", CUSTOMER);
    }

    @JsonCreator
    public static UserEnum fromString(String value) {
        return mapping.get(value);
    }
};
