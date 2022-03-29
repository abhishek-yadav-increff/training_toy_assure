package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinSkuData extends BinSkuForm {

    private Long id;
    private Long globalSkuId;

    @Override
    public String toString() {
        return "BinSkuData [globalSkuId=" + globalSkuId + ", id=" + id + ", binId=" + getBinId()
                + ", clientId=" + getClientId() + ", clientSkuId=" + getClientSkuId()
                + ", quantity=" + getQuantity() + "]";
    }
}
