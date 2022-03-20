package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.increff.assure.model.ZonedDateTimeData;

/**
 * ZonedDateTimeDtoHelper
 */
public class ZonedDateTimeDtoHelper {

    public static List<ZonedDateTimeData> convert(Map<String, String> zonedDateTimeMap) {
        List<ZonedDateTimeData> zonedDateTimeDatas = new ArrayList<>();
        for (Map.Entry<String, String> entry : zonedDateTimeMap.entrySet()) {
            ZonedDateTimeData zonedDateTimeData =
                    new ZonedDateTimeData(entry.getKey(), entry.getValue());
            zonedDateTimeDatas.add(zonedDateTimeData);
        }
        return zonedDateTimeDatas;
    }

}
