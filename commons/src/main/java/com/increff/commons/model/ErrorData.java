package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorData
 */
@Getter
@Setter
public class ErrorData {
    private String status;
    private String errorMessage;
    private Long row;

    public ErrorData(String status, String errorMessage, Long row) {
        this.row = row;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ErrorData() {}

    @Override
    public String toString() {
        return "ErrorData [errorMessage=" + errorMessage + ", row=" + row + ", status=" + status
                + "]";
    }

}
