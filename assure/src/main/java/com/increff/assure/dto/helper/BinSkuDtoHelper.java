package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.pojo.BinSkuPojo;

/**
 * BinSkuDtoHelper
 */
public class BinSkuDtoHelper {

    public static BinSkuData convert(BinSkuPojo clientPojo) {
        BinSkuData clientData = new BinSkuData();
        clientData.setId(clientPojo.getId());
        clientData.setBinId(clientPojo.getBinId());
        clientData.setGlobalSkuId(clientPojo.getGlobalSkuId());
        clientData.setQuantity(clientPojo.getQuantity());
        return clientData;
    }

    public static BinSkuPojo convert(BinSkuForm clientForm, Long globalSkuId) {
        BinSkuPojo clientPojo = new BinSkuPojo();
        clientPojo.setBinId(clientForm.getBinId());
        clientPojo.setGlobalSkuId(globalSkuId);
        clientPojo.setQuantity(clientForm.getQuantity());
        return clientPojo;
    }

    public static List<BinSkuData> convert(List<BinSkuPojo> clientPojos) {
        List<BinSkuData> clientDatas = new ArrayList<BinSkuData>();
        for (BinSkuPojo clientPojo : clientPojos) {
            clientDatas.add(convert(clientPojo));
        }
        return clientDatas;
    }

}
