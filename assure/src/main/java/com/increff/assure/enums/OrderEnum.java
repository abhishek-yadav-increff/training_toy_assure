package com.increff.assure.enums;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderEnum {
    CREATED, ALLOCATED, FULFILLED;

    private static Map<String, OrderEnum> mapping = new HashMap<>();
    static {
        mapping.put("created", CREATED);
        mapping.put("allocated", ALLOCATED);
        mapping.put("fulfilled", FULFILLED);
    }

    @JsonCreator
    public static OrderEnum fromString(String value) {
        return mapping.get(value);
    }
};
