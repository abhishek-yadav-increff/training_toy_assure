package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * BinForm
 */
@Getter
@Setter
public class BinForm {

    private Long binSize;

    @Override
    public String toString() {
        return "BinForm [binSize=" + binSize + "]";
    }
}
