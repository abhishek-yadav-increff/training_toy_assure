package com.increff.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorData
 */
@Getter
@Setter
public class ErrorData {
    private String status;
    private String message;

    public ErrorData(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
