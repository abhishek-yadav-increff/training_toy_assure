package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * ZonedDateTimeData
 */
@Setter
@Getter
public class ZonedDateTimeData {
    public String zoneDateTime;
    public String offset;

    public ZonedDateTimeData(String zoneDateTime, String offset) {
        this.zoneDateTime = zoneDateTime;
        this.offset = offset;
    }
}
