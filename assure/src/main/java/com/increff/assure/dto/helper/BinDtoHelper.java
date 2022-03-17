package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.model.BinData;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.pojo.BinPojo;

/**
 * BinDtoHelper
 */
public class BinDtoHelper {

    public static BinData convert(BinPojo binPojo) {
        BinData binData = new BinData();
        binData.setBinId(binPojo.getBinId());
        return binData;
    }

    public static List<BinData> convert(List<BinPojo> binPojos) {
        List<BinData> binDatas = new ArrayList<BinData>();
        for (BinPojo binPojo : binPojos) {
            binDatas.add(convert(binPojo));
        }
        return binDatas;
    }

    public static BinIndexRange convertToRange(Long smIndex, Long size) {
        return new BinIndexRange(smIndex + 1, smIndex + size);
    }

}
