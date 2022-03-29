package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * BinSkuForm
 */
@Setter
@Getter
public class BinSkuForm {
    private Long binId;
    private String clientSkuId;
    private Long clientId;
    private Long quantity;

    @Override
    public String toString() {
        return "BinSkuForm [binId=" + binId + ", clientId=" + clientId + ", clientSkuId="
                + clientSkuId + ", quantity=" + quantity + "]";
    }
}
