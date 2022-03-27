package com.increff.assure.utils;

import com.increff.assure.model.BinSkuForm;

/**
 * BinSkuUtils
 */
public class BinSkuUtils {

    public static BinSkuForm getBinSkuForm(Long binId, String clientSkuId, Long clientId,
            Long quantity) {
        BinSkuForm binSkuForm = new BinSkuForm();
        binSkuForm.setBinId(binId);
        binSkuForm.setClientId(clientId);
        binSkuForm.setClientSkuId(clientSkuId);
        binSkuForm.setQuantity(quantity);
        return binSkuForm;
    }
}
