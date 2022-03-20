package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * BinSkuErrorData
 */
@Getter
@Setter
public class BinSkuReturnData {
    private Long row;
    private String message;
    private String type;

    public BinSkuReturnData(Long row, String message, String type) {
        this.row = row;
        this.message = message;
        this.type = type;
    }
}
