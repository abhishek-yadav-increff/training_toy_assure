package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * BinForm
 */
@Getter
@Setter
public class BinData {

    private Long binId;

    @Override
    public String toString() {
        return "BinData [binId=" + binId + "]";
    }

}
